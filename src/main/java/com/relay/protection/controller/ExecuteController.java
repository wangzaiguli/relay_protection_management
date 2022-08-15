package com.relay.protection.controller;

import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.pojo.input.OrderFlowInput;
import com.relay.protection.pojo.input.PageInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.OrderInfoOutput;
import com.relay.protection.pojo.output.OrderListOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.FileService;
import com.relay.protection.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/15 14:32
 */
@ApiOperation("执行")
@RestController
@RequestMapping("execute")
public class ExecuteController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FileService fileService;


    @PostMapping("confirm")
    @ApiOperation("确认")
    @PreAuthorize("hasAuthority('execute_confirm')")
    public JsonResult<Integer> confirm(@RequestHeader("token") String token, @RequestBody OrderFlowInput input) {
        return orderService.executeSuc(input, FlowEnum.EXECUTE_SUC.getKey());
    }

    @PostMapping("debugSuc")
    @ApiOperation("调试成功")
    @PreAuthorize("hasAuthority('execute_debug_suc')")
    public JsonResult<Integer> debugSuc(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        OrderFlowInput input = new OrderFlowInput();
        input.setId(id);
        return orderService.next(input, FlowEnum.DEBUG_SUC.getKey(), FlowEnum.DEBUG_SUC.getKey());
    }

    @PostMapping("reject")
    @ApiOperation("驳回")
    @PreAuthorize("hasAuthority('execute_reject')")
    public JsonResult<Integer> reject(@RequestHeader("token") String token, @RequestBody() OrderFlowInput input) {
        return orderService.reject(input, FlowEnum.EXECUTE.getKey());
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('execute_list')")
    public JsonResult<PageOutput<OrderListOutput>> queryList(@RequestHeader("token") String token, PageInput vo) {
        return orderService.queryList(vo, FlowEnum.EXECUTE.getKey());
    }

    @GetMapping("info")
    @ApiOperation("查询详情")
    @PreAuthorize("hasAuthority('execute_info')")
    public JsonResult<OrderInfoOutput> queryList(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderService.queryInfo(id);
    }

    @PostMapping("upload")
    @ApiOperation("上传")
    @PreAuthorize("hasAuthority('execute_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('execute_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }

}