package com.relay.protection.service;

import com.alibaba.fastjson.JSON;
import com.relay.protection.pojo.dto.UserAuth;
import com.relay.protection.pojo.output.LoginOutput;
import com.relay.protection.pojo.output.MenuOutput;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.init.MenuInit;
import com.relay.protection.common.util.JwtUtil;
import com.relay.protection.service.common.LoginUser;
import com.relay.protection.pojo.input.LoginInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@Service
@Slf4j
public class LoginService extends BaseService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public JsonResult<LoginOutput> login(LoginInput vo) {
        //1.通过AuthenticationManager的authenticate方法来进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(vo.getUserName(), vo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new BizException(JsonReturnCode.USET_ERR);
        }
        //2.使用userid生成token
        LoginOutput showVo = new LoginOutput();
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        showVo.setUserName(loginUser.getUsername());
        MenuOutput tree = MenuInit.getRoot();
        MenuInit.setTree(tree, loginUser.getPermissions().stream().map(UserAuth::getCode).collect(Collectors.toSet()));
        showVo.setMenuTree(tree);
        loginUser.setPermissions(null);
        showVo.setToken(JwtUtil.createJWT(JSON.toJSONString(loginUser)));
        return JsonResult.success(showVo);
    }

    public JsonResult<Void> logout() {
        //登陆成功后，会将用户信息存入SecurityContextholder,所以可以从SecurityContextholder中获取用户信息
        SecurityContextHolder.clearContext();
        return JsonResult.success();
    }


}
