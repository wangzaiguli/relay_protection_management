package com.relay.protection.common.error;

import com.alibaba.fastjson.JSON;
import com.relay.protection.common.util.WebUtils;
import com.relay.protection.pojo.output.JsonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        JsonResult<String> fail = JsonResult.fail(JsonReturnCode.SESSION_EXPIRED);
        String json = JSON.toJSONString(fail);
        WebUtils.renderString(httpServletResponse, json);
    }
}
