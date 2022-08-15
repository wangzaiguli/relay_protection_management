package com.relay.protection.common.error;


import lombok.Data;

/**
 * 运行时业务异常
 */
@Data
public class BizException extends RuntimeException {

    public static final String ERROR_MSG = "执行错误";

    public static final String ERROR_KEY = "msg";

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
     *
     * @param jsonReturnCode jsonReturnCode
     */
    public BizException(JsonReturnCode jsonReturnCode, String... values) {
        if (values != null) {
            this.msg = String.format(jsonReturnCode.getMsg(), values);
        } else {
            this.msg = jsonReturnCode.getMsg();
        }
        this.code = jsonReturnCode.getCode();
    }

    /**
     * 异常信息(只需要错误码，错误信息重新输入)
     *
     * @param jsonReturnCode
     * @param msg            错误信息
     */
    public BizException(JsonReturnCode jsonReturnCode, String msg) {
        this.code = jsonReturnCode.getCode();
        this.msg = msg;
    }

    /**
     * 其他类型的操作失败
     *
     * @param errMsg 错误信息
     * @return
     */
    public BizException(String errMsg) {
        this.code = JsonReturnCode.BIZ_OTHER_ERROR.getCode();
        this.msg = errMsg;
    }

    /**
     * 其他类型的操作失败
     */
    public BizException() {
        this.code = JsonReturnCode.BIZ_OTHER_ERROR.getCode();
        this.msg = JsonReturnCode.BIZ_OTHER_ERROR.getMsg();
    }


}
