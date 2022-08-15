package com.relay.protection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.relay.protection.pojo.bo.SysMethod;
import com.relay.protection.pojo.dto.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMethodMapper extends BaseMapper<SysMethod> {


}