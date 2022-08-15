package com.relay.protection.config;

import com.alibaba.fastjson.JSON;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.FileException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 通用错误处理
 *
 * @author zhuHx
 */
@ControllerAdvice
@Slf4j
public class AdvanceController extends BaseService {

    /**
     * 业务异常处理机制
     *
     * @param bizException bizException
     * @return JsonResult
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public <T> JsonResult<T> bizException(BizException bizException) throws IOException {
        if (JsonReturnCode.SESSION_EXPIRED.getCode().equals(bizException.getCode())) {
            HttpServletResponse response = response();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(ContentType.APPLICATION_JSON.toString());
            try (Writer writer = response.getWriter()) {
                writer.write(JSON.toJSONString(JsonResult.fail(JsonReturnCode.SESSION_EXPIRED)));
                writer.flush();
            }
            return null;
        } else {
            return JsonResult.fail(bizException);
        }
    }

    /**
     * 文件异常
     *
     * @param e e
     * @return JsonResult
     */
    @ExceptionHandler(value = FileException.class)
    public <T> JsonResult<T> fileException(FileException e) {
//        log.error(BizException.ERROR_MSG, e);
        return JsonResult.fail(e.getCode(), e.getMsg());
    }

    /**
     * 通用异常处理机制
     *
     * @param e e
     * @return JsonResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> JsonResult<T> handleException(Exception e) {
        log.error(BizException.ERROR_MSG, e);
        return JsonResult.fail(JsonReturnCode.SYSTEM_ERROR);
    }

    /**
     * 通用异常处理机制
     *
     * @param e e
     * @return JsonResult
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public <T> JsonResult<T> handleException(AuthenticationException e) {
        log.error(BizException.ERROR_MSG, e);

        Throwable cause = e.getCause();
        if (cause instanceof BizException) {
            return JsonResult.fail((BizException) cause);
        }
        return JsonResult.fail(JsonReturnCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public <T> JsonResult<T> exceptionHandler(AccessDeniedException e) {
        log.error(BizException.ERROR_MSG, e);
        return JsonResult.fail(JsonReturnCode.NO_AUTH_ERR);
    }


    /**
     * 通用异常处理机制
     *
     * @param e e
     * @return JsonResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> JsonResult<T> handleValidException(MethodArgumentNotValidException e) {
        log.error(BizException.ERROR_MSG, e);
        return JsonResult.fail(JsonReturnCode.VALID_ERROR.getCode(), e.getMessage());
    }

}
