package com.relay.protection.controller;

import com.relay.protection.common.enums.MenuTypeEnum;
import com.relay.protection.pojo.dto.TemplateItem;
import com.relay.protection.pojo.input.CalcInput;
import com.relay.protection.pojo.input.TemplateDirSaveInput;
import com.relay.protection.pojo.input.TemplateDirUpdateInput;
import com.relay.protection.pojo.input.TemplateSaveInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.OrderTemplateOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.FileService;
import com.relay.protection.service.OrderTemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@RestController
@RequestMapping("template")
public class OrderTemplateController {

    @Autowired
    private OrderTemplateService orderTemplateService;

    @Autowired
    private FileService fileService;

    @PostMapping("dir")
    @ApiOperation("新增目录")
    @PreAuthorize("hasAuthority('template_save_dir')")
    public JsonResult<Integer> saveDir(@RequestHeader("token") String token, @Valid @RequestBody TemplateDirSaveInput vo) {
        return orderTemplateService.saveDir(vo, MenuTypeEnum.M.getKey());
    }

    @PostMapping("file")
    @ApiOperation("新增文件")
    @PreAuthorize("hasAuthority('template_save_file')")
    public JsonResult<Integer> saveFile(@RequestHeader("token") String token, @Valid @RequestBody TemplateSaveInput vo) {
        return orderTemplateService.saveFile(vo, MenuTypeEnum.I.getKey());
    }

    @PutMapping("dir")
    @ApiOperation("修改目录")
    @PreAuthorize("hasAuthority('template_update')")
    public JsonResult<Integer> updete(@RequestHeader("token") String token, @Valid @RequestBody TemplateDirUpdateInput vo) {
        return orderTemplateService.update(vo);
    }

    @GetMapping("list")
    @ApiOperation("查询目录文件")
    @PreAuthorize("hasAuthority('template_list')")
    public JsonResult<OrderTemplateOutput> queryList(@RequestHeader("token") String token) {
        return orderTemplateService.queryList();
    }

    @DeleteMapping("dir")
    @ApiOperation("删除目录")
    @PreAuthorize("hasAuthority('template_delete_dir')")
    public JsonResult<Integer> deleteDir(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderTemplateService.deleteDir(id);
    }

    @DeleteMapping("file")
    @ApiOperation("删除文件")
    @PreAuthorize("hasAuthority('template_delete_file')")
    public JsonResult<Integer> deleteFile(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderTemplateService.deleteFile(id);
    }

    @GetMapping("caclList")
    @ApiOperation("历史记录")
    @PreAuthorize("hasAuthority('template_cacl_list')")
    public JsonResult<PageOutput<List<TemplateItem>>> caclList(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return orderTemplateService.caclList(id);
    }

    @PostMapping("cacl")
    @ApiOperation("计算")
    @PreAuthorize("hasAuthority('template_cacl')")
    public JsonResult<Void> cacl(@RequestHeader("token") String token, @RequestBody CalcInput input) {
        return orderTemplateService.cacl(input);
    }

    @PostMapping("upload")
    @ApiOperation("上传")
    @PreAuthorize("hasAuthority('template_upload')")
    public JsonResult<Long> upload(@RequestHeader("token") String token, @RequestParam("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("download")
    @ApiOperation("下载")
    @PreAuthorize("hasAuthority('template_download')")
    public JsonResult<Void> download(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return fileService.download(id);
    }


}
