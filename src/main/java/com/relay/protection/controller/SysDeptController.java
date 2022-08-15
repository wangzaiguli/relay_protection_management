package com.relay.protection.controller;

import com.relay.protection.pojo.input.DeptSaveInput;
import com.relay.protection.pojo.input.DeptUpdateInput;
import com.relay.protection.pojo.output.DeptOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.SysDeptService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('dept_save')")
    public JsonResult<Long> save(@RequestHeader("token") String token, @Valid @RequestBody DeptSaveInput vo) {
        return sysDeptService.save(vo);
    }

    @PutMapping("info")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('dept_update')")
    public JsonResult<Long> updete(@RequestHeader("token") String token, @Valid @RequestBody DeptUpdateInput vo) {
        return sysDeptService.update(vo);
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('dept_list')")
    public JsonResult<DeptOutput> queryList(@RequestHeader("token") String token) {
        return sysDeptService.queryList();
    }


    @DeleteMapping("info")
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('dept_delete')")
    public JsonResult<Integer> delete(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return sysDeptService.delete(id);
    }


}
