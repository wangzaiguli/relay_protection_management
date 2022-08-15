package com.relay.protection.controller;

import com.relay.protection.pojo.input.*;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.pojo.output.RoleOutput;
import com.relay.protection.pojo.output.UserOutput;
import com.relay.protection.service.SysUserService;
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
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("info")
    @ApiOperation("新增")
    @PreAuthorize("hasAuthority('user_save')")
    public JsonResult<Long> save(@RequestHeader("token") String token, @Valid @RequestBody UserSaveInput vo) {
        return sysUserService.save(vo);
    }

    @PutMapping("info")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('user_update')")
    public JsonResult<Long> updete(@RequestHeader("token") String token, @Valid @RequestBody UserUpdateInput vo) {
        return sysUserService.updete(vo);
    }

    @GetMapping("info")
    @ApiOperation("查询详情")
    @PreAuthorize("hasAuthority('user_info')")
    public JsonResult<UserOutput> query(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return sysUserService.query(id);
    }

    @GetMapping("list")
    @ApiOperation("查询列表")
    @PreAuthorize("hasAuthority('user_list')")
    public JsonResult<PageOutput<UserOutput>> queryList(@RequestHeader("token") String token, UserQueryInput vo) {
        return sysUserService.queryList(vo);
    }

    @DeleteMapping("info")
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('user_delete')")
    public JsonResult<Integer> delete(@RequestHeader("token") String token, @RequestParam("id") Long id) {
        return sysUserService.delete(id);
    }

//    @PutMapping("role")
//    @ApiOperation("修改角色")
//    @PreAuthorize("hasAuthority('user_role_update')")
//    public JsonResult<Integer> updeteUserRole(@RequestHeader("token") String token, @Valid @RequestBody UserRoleUpdateInput vo) {
//        return sysUserService.updeteUserRole(vo);
//    }
//
//    @GetMapping("role")
//    @ApiOperation("查询角色")
//    @PreAuthorize("hasAuthority('user_role_info')")
//    public JsonResult<PageOutput<RoleOutput>> queryRoleMenu(@RequestHeader("token") String token, @RequestParam("roleId") Long roleId) {
//        return sysUserService.queryUserRole(roleId);
//    }

    @PutMapping("push/token")
    @ApiOperation("推送token添加")
    @PreAuthorize("hasAuthority('user_push_token')")
    public JsonResult<Integer> saveToken(@RequestHeader("token") String token, @RequestParam("pushToken") String pushToken) {
        return sysUserService.saveToken(pushToken);
    }

    @GetMapping("push/test")
    @ApiOperation("推送测试")
    @PreAuthorize("hasAuthority('user_push_test')")
    public JsonResult<String> testPush(@RequestHeader("token") String token) {
        return sysUserService.testPush();
    }
}
