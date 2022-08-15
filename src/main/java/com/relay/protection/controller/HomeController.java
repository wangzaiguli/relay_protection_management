package com.relay.protection.controller;

import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.pojo.input.PageInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.OrderListOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.HomeService;
import com.relay.protection.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/8/5 15:07
 */
@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('home_list')")
    public JsonResult<PageOutput<OrderListOutput>> queryHomeList(@RequestHeader("token") String token) {
        return orderService.queryHomeList();
    }

}
