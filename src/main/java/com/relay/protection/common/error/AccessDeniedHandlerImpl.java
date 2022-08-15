package com.relay.protection.common.error;

import com.alibaba.fastjson.JSON;
import com.relay.protection.common.util.WebUtils;
import com.relay.protection.pojo.output.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JsonResult<String> fail = JsonResult.fail(JsonReturnCode.NO_AUTH_ERR);
        String json = JSON.toJSONString(fail);
        WebUtils.renderString(httpServletResponse, json);
    }
}
