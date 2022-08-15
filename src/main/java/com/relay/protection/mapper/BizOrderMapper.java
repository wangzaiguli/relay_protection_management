package com.relay.protection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.relay.protection.pojo.bo.BizOrder;
import com.relay.protection.pojo.dto.OrderQueryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizOrderMapper extends BaseMapper<BizOrder> {

    BizOrder queryInfo(Long id);


    List<BizOrder> applyQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E applyQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> calcQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E calcQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> checkQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E checkQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> verifyQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E verifyQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> approvalQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E approvalQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> executeQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E executeQuery(E page, @Param("dto") OrderQueryDto dto);


    List<BizOrder> allQuery(@Param("dto") OrderQueryDto dto);

    <E extends IPage<BizOrder>> E allQuery(E page, @Param("dto") OrderQueryDto dto);

}