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
import io.swagger.annotations.Api;
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
@Api("审批")
@RestController
@RequestMapping("approval")
public class ApprovalController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FileService fileService;

    @PostMapping("confirm")
    @ApiOperation("确认")
    @PreAuthorize("hasAuthority('approval_confirm')")
    public JsonResult<Integer> confirm(@RequestHeader("token") String token, @RequestBody OrderFlowInput input) {
        return orderService.confirm(input, FlowEnum.APPROVAL.getKey());
    }

    @PostMapping("reject")
    @ApiOperation("驳回")
    @PreAuthorize("hasAuthority('approval_reject')")
    public JsonResult<Integer> reject(@RequestHeader("token") String token, @RequestBody() OrderFlowInput input) {
        return orderService.reject(input, FlowEnum.APPROVAL.getKey());
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('approval_list')")
    public JsonResult<PageOutput<OrderListOutput>> queryList(@RequestHeader("token") String token, PageInput vo) {
        return orderService.queryList(vo, FlowEnum.APPROVAL.getKey());
    }

    @GetMapping("info")
    @ApiOperation("查询详情")
    @PreAuthorize("hasAuthority('approval_info')")
    public JsonResult<OrderInfoOutput> queryList(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderService.queryInfo(id);
    }

    @PostMapping("upload")
    @ApiOperation("上传")
    @PreAuthorize("hasAuthority('approval_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('approval_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }

}