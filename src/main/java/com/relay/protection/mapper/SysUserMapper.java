package com.relay.protection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.relay.protection.pojo.bo.SysUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SysUserMapper  extends BaseMapper<SysUser> {

    @Select("SELECT * FROM sys_user WHERE user_name = #{userName}")
    @Results({
            @Result(column = "user_name", property = "userName"),
            @Result(column = "nick_name", property = "nickName")
    })
    SysUser getUserByUserName(@Param("userName") String userName);
}