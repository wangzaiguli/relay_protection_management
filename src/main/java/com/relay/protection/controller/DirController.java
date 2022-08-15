package com.relay.protection.controller;

import com.relay.protection.pojo.input.DirSaveInput;
import com.relay.protection.pojo.input.DirUpdateInput;
import com.relay.protection.pojo.output.DirOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.DirService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@RestController
@RequestMapping("dir")
public class DirController {

    @Autowired
    private DirService dirService;

    @PostMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('dir_save')")
    public JsonResult<Integer> save(@RequestHeader("token") String token, @Valid @RequestBody DirSaveInput vo) {
        return dirService.save(vo);
    }

    @PutMapping("info")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('dir_update')")
    public JsonResult<Integer> updete(@RequestHeader("token") String token, @Valid @RequestBody DirUpdateInput vo) {
        return dirService.update(vo);
    }

    @GetMapping("list")
    @ApiOperation("查询目录树")
    @PreAuthorize("hasAuthority('dir_list')")
    public JsonResult<DirOutput> queryList(@RequestHeader("token") String token,
                                           @ApiParam(required = true, name = "view", value = "true:只显示目录维护的； false:显示全部的，包括定值单的")
                                           @RequestParam(value = "view") Boolean view) {
        return dirService.queryList(view);
    }

    @DeleteMapping("info")
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('dir_delete')")
    public JsonResult<Integer> delete(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return dirService.delete(id);
    }

}
