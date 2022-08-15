package com.relay.protection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.relay.protection.pojo.bo.SysMenu;
import com.relay.protection.pojo.dto.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<UserAuth> getRoleMenuList(@Param("id") Long id);

    List<UserAuth> getUserMenuList(@Param("id") Long id);

    List<UserAuth> getUserMenuAllList();

}