package com.relay.protection.controller;

import com.relay.protection.pojo.input.RoleQueryInput;
import com.relay.protection.pojo.input.RoleSaveInput;
import com.relay.protection.pojo.input.RoleUpdateInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.MenuOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.pojo.output.RoleOutput;
import com.relay.protection.service.SysRoleService;
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
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @PostMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('role_save')")
    public JsonResult<Long> register(@RequestHeader("token") String token, @Valid @RequestBody RoleSaveInput vo) {
        return sysRoleService.save(vo);
    }

    @PutMapping("info")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('role_update')")
    public JsonResult<Long> updete(@RequestHeader("token") String token, @Valid @RequestBody RoleUpdateInput vo) {
        return sysRoleService.updete(vo);
    }

    @GetMapping("info")
    @ApiOperation("查询详情")
    @PreAuthorize("hasAuthority('role_info')")
    public JsonResult<RoleOutput> query(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return sysRoleService.query(id);
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('role_list')")
    public JsonResult<PageOutput<RoleOutput>> queryList(@RequestHeader("token") String token, RoleQueryInput vo) {
        return sysRoleService.queryList(vo);
    }

    @DeleteMapping("info")
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('role_delete')")
    public JsonResult<Integer> delete(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return sysRoleService.delete(id);
    }

    @GetMapping("menu")
    @ApiOperation("查询空菜单， 新增用")
    @PreAuthorize("hasAuthority('role_menu_info')")
    public JsonResult<MenuOutput> queryRoleMenu(@RequestHeader("token") String token) {
        return sysRoleService.queryRoleMenu();
    }

}
