package com.relay.protection.controller;

import com.relay.protection.pojo.input.UpdatePasswordInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.FileService;
import com.relay.protection.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
 * @date 2022/7/26 15:40
 */
@RestController
@RequestMapping("set")
public class SetController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileService fileService;

    @PutMapping("password")
    @ApiOperation("修改密码")
    @PreAuthorize("hasAuthority('user_password')")
    public JsonResult<Integer> updetePassword(@RequestHeader("token") String token, UpdatePasswordInput vo) {
        return sysUserService.updetePassword(vo);
    }

    @PutMapping("signature")
    @ApiOperation("修改签章")
    @PreAuthorize("hasAuthority('user_signature')")
    public JsonResult<Integer> updeteSignature(@RequestHeader("token") String token, @Param("signature") Long signature) {
        return sysUserService.updeteSignature(signature);
    }

    @PostMapping("upload")
    @ApiOperation("上传签章")
    @PreAuthorize("hasAuthority('signature_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('signature_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }

}
