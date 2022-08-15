package com.relay.protection.controller;

import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.common.util.Constant;
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
@Api("申请")
@RestController
@RequestMapping("apply")
public class ApplyController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FileService fileService;

    @PostMapping("save")
    @ApiOperation("保存")
    @PreAuthorize("hasAuthority('apply_save')")
    public JsonResult<Integer> save(@RequestHeader("token") String token, @RequestBody OrderFlowInput input) {
        return orderService.save(input);
    }

    @PostMapping("confirm")
    @ApiOperation("确认")
    @PreAuthorize("hasAuthority('apply_confirm')")
    public JsonResult<Integer> confirm(@RequestHeader("token") String token, @RequestBody OrderFlowInput input) {
        return orderService.applyConfirm(input);
    }

    @PostMapping("cancel")
    @ApiOperation("取消")
    @PreAuthorize("hasAuthority('apply_cancel')")
    public JsonResult<Integer> cancel(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderService.cancel(id);
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('apply_list')")
    public JsonResult<PageOutput<OrderListOutput>> queryList(@RequestHeader("token") String token, PageInput vo) {
        return orderService.queryList(vo, FlowEnum.APPLY.getKey());
    }

    @GetMapping("info")
    @ApiOperation("查询详情")
    @PreAuthorize("hasAuthority('apply_info')")
    public JsonResult<OrderInfoOutput> queryList(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderService.queryInfo(id);
    }

    @PostMapping("upload")
    @ApiOperation("上传")
    @PreAuthorize("hasAuthority('apply_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('apply_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }

}
