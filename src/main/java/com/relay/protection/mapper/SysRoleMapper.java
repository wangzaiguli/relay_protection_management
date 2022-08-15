package com.relay.protection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.relay.protection.pojo.bo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper  extends BaseMapper<SysRole> {

    List<SysRole> getUserRoleList(@Param("id") Long id);

}