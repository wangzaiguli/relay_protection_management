package com.relay.protection.controller;

import com.relay.protection.common.init.SelectInit;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.common.CommonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("select")
    @ApiOperation("查询下拉表")
    public JsonResult<Map<String, List<SelectInit.Node>>> getSelect(
            @ApiParam(value = "flow:状态； menuType:目录/接口；decide：是否可用")
            @RequestParam Set<String> set) {
        return commonService.getSelect(set);
    }

    @GetMapping("log")
    @ApiOperation("获取日志")
    public JsonResult<Void> getLog() {
        return commonService.getLog();
    }


}
