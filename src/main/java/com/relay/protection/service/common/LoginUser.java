package com.relay.protection.service.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.relay.protection.pojo.bo.SysUser;
import com.relay.protection.pojo.dto.UserAuth;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * LoginUser
 */
@Data
@NoArgsConstructor

public class LoginUser implements UserDetails {

    private SysUser user;

    //角色集合
    private Set<Long> roleIds;

    //存储此用户的权限信息
    private List<UserAuth> permissions;


    //存储SpringSecuity所需要的权限的集合
    //redis为了安全考虑 不序列化spring中的对象（这里前面也遇到过这样的问题即使添加白名单也不行）
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;


    public LoginUser(SysUser user, List<UserAuth> permissions, Set<Long> roleIds) {
        this.user = user;
        this.permissions = permissions;
        this.roleIds = roleIds;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        //防止每次访问接口都要创建一个集合所导致的内存压力，故将其当作成员变量
        authorities = permissions.stream().map(item -> new SimpleGrantedAuthority(item.getPerms())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}