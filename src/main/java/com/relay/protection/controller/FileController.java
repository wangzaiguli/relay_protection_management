package com.relay.protection.controller;

import com.relay.protection.pojo.input.*;
import com.relay.protection.pojo.output.DeptOutput;
import com.relay.protection.pojo.output.FileOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.DataFileService;
import com.relay.protection.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private FileService fileService;

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('file_list')")
    public JsonResult<PageOutput<FileOutput>> queryList(@RequestHeader("token") String token, FileQueyInput vo) {
        return fileService.queryList(vo);
    }

    @PostMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('file_save')")
    public JsonResult<Integer> save(@RequestHeader("token") String token, @Valid @RequestBody FileSaveInput vo) {
        return fileService.save(vo);
    }

    @DeleteMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('file_del')")
    public JsonResult<Integer> delete(@RequestHeader("token") String token, @Param("id") Long id) {
        return fileService.delete(id);
    }

    @PostMapping("upload")
    @ApiOperation("上传")
    @PreAuthorize("hasAuthority('file_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('file_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }

}
