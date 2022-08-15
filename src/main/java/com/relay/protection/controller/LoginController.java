package com.relay.protection.controller;

import com.relay.protection.pojo.input.LoginInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.LoginOutput;
import com.relay.protection.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/5/23 16:34
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation("登录")
    @PostMapping("/login")
    public JsonResult<LoginOutput> login(@RequestBody LoginInput vo) {
        return loginService.login(vo);
    }

    @ApiOperation("退出")
    @GetMapping("/logout")
    public JsonResult<Void> logout() {
        return loginService.logout();
    }

}
