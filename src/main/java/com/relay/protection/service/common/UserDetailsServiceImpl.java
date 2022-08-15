package com.relay.protection.service.common;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.mapper.SysMenuMapper;
import com.relay.protection.mapper.SysMethodMapper;
import com.relay.protection.mapper.SysUserRoleMapper;
import com.relay.protection.pojo.bo.SysUserRole;
import com.relay.protection.pojo.dto.UserAuth;
import com.relay.protection.mapper.SysUserMapper;
import com.relay.protection.pojo.bo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    //从数据库验证用户 而不是官方的user+控制台输出的密码
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.getUserByUserName(username);
        if (Objects.isNull(user)) {
            throw new BizException(JsonReturnCode.USET_ERR);
        }
        List<UserAuth> userAuthList = sysMenuMapper.getUserMenuList(user.getId());
        Set<Long> roleIds = userAuthList.stream().map(UserAuth::getRoleId).collect(Collectors.toSet());
        return new LoginUser(user,userAuthList, roleIds);
    }
}