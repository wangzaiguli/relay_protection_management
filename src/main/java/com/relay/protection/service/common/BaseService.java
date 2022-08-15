package com.relay.protection.service.common;

import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.init.FlowInit;
import com.relay.protection.common.init.SelectInit;
import com.relay.protection.common.util.Constant;
import com.relay.protection.pojo.converter.DtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * .
 *
 * @author zhuHx
 * @version 1.0
 * @date 2022/5/27 10:51
 */
@Service
@Slf4j
public class BaseService {

    @Autowired
    private HttpSession session;

    @Autowired
    protected DtoConverter dtoConverter;

    @Autowired
    protected SelectInit selectInit;

    @Autowired
    protected FlowInit flowInit;

    @Value("${mydata.file}")
    private String filePath;

    private String filePathStr;

    @PostConstruct
    public void initService() {
        filePathStr = filePath.replace(Constant.SLASH, File.separator);
    }

    protected String filePath() {
        return filePathStr;
    }

    protected LoginUser getUserInfo() {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取请求信息
     *
     * @return
     */
    private ServletRequestAttributes getRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes;
    }

    /**
     * 获取request
     *
     * @return
     */
    protected HttpServletRequest request() {
        return this.getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     *
     * @return
     */
    protected HttpServletResponse response() {
        return this.getRequestAttributes().getResponse();
    }

    /**
     * 获取输出流
     *
     * @param contentType 返回前台的文件类型
     * @return
     */
    protected OutputStream getOutPutStream(ContentType contentType) {
        HttpServletResponse response = this.response();
        response.setContentType(contentType.toString());
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.IO_ERROR);
        }
    }

    /**
     * 服务地址
     *
     * @return
     */
    protected String serverPath() {
        String url = request().getRequestURL().toString();
        String uri = request().getRequestURI();
        url = url.replace(uri, "");
        return url;
    }

    /**
     * 服务地址
     *
     * @return
     */
    protected String requestUri() {
        return request().getRequestURI();
    }

}
