package com.relay.protection.config;


import com.alibaba.fastjson.JSON;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.init.RoleInit;
import com.relay.protection.common.util.JwtUtil;
import com.relay.protection.pojo.dto.UserAuth;
import com.relay.protection.service.common.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
//这里采用继承OncePerRequestFilter的方式来自定义过滤器，而不使用实现Filter接口的方式，原因是前一种方式可以避免请求被多次拦截
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RoleInit roleInit;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //2.解析token
        String userInfo;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userInfo = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.TOKEN_GET_ERR);
        }
        //3.获取用户信息
        LoginUser loginUser = JSON.parseObject(userInfo, LoginUser.class);
        if (Objects.isNull(userInfo)) {
            throw new BizException(JsonReturnCode.TOKEN_ERR);
        }

        // 把权限放入user
        Set<Long> roleIdSet = loginUser.getRoleIds();
        List<UserAuth> list = roleInit.userAuthMap.entrySet()
                .stream().filter(item -> roleIdSet.contains(item.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        loginUser.setPermissions(list);

        //4.存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
