package com.relay.protection.common.error;


import lombok.Data;

/**
 * 文件下载异常
 */
@Data
public class FileException extends RuntimeException {

    /**
     * 运行时code
     */
    private String code;

    /**
     * 运行时消息
     */
    private String msg;

    /**
     * 异常信息
     * @param jsonReturnCode
     */
    public FileException(JsonReturnCode jsonReturnCode){
        this.code = jsonReturnCode.getCode();
        this.msg = jsonReturnCode.getMsg();
    }

}
