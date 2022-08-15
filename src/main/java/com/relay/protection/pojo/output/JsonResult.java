package com.relay.protection.pojo.output;


import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述: json格式数据返回对象，使用CustomJsonResultSerializer 来序列化
 *
 * @author zhuHx
 */
@Data
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "返回码")
    private String code;

    @ApiModelProperty(value = "返回提示语句")
    private String msg;

    @ApiModelProperty(value = "返回信息")
    private T data;

    /**
     * 成功--提示信息
     *
     * @param msg msg
     * @param <T> t
     * @return JsonResult
     */
    public static <T> JsonResult<T> successMessage(String msg) {
        JsonResult<T> ret = JsonResult.success();
        ret.setMsg(msg);
        return ret;
    }

    /**
     * 成功--
     *
     * @param <T> t
     * @return JsonResult
     */
    public static <T> JsonResult<T> success() {
        JsonResult<T> ret = new JsonResult<>();
        ret.setCode(JsonReturnCode.BIZ_SUCCESS.getCode());
        ret.setMsg(JsonReturnCode.BIZ_SUCCESS.getMsg());
        return ret;
    }

    /**
     * 成功--数据
     *
     * @param data data
     * @param <T>  t
     * @return JsonResult
     */
    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> ret = JsonResult.success();
        ret.setData(data);
        return ret;
    }


    /**
     * 失败--
     *
     * @param <T> t
     * @return JsonResult
     */
    public static <T> JsonResult<T> fail() {
        JsonResult<T> ret = new JsonResult<>();
        ret.setCode(JsonReturnCode.BIZ_FAIL.getCode());
        ret.setMsg(JsonReturnCode.BIZ_FAIL.getMsg());
        return ret;
    }

    /**
     * 失败--码，信息
     *
     * @param code 返回码
     * @param msg  返回提示语句
     * @param <T>  t
     * @return JsonResult
     */
    public static <T> JsonResult<T> fail(String code, String msg) {
        JsonResult<T> ret = new JsonResult<>();
        ret.setCode(code);
        ret.setMsg(msg);
        return ret;
    }

    /**
     * 失败--json格式数据返回码
     *
     * @param jsonReturnCode jsonReturnCode
     * @param <T>            t
     * @return JsonResult
     */
    public static <T> JsonResult<T> fail(JsonReturnCode jsonReturnCode) {
        return fail(jsonReturnCode.getCode(), jsonReturnCode.getMsg());
    }

    /**
     * 失败--异常
     *
     * @param bizexception e
     * @param <T>          t
     * @return JsonResult
     */
    public static <T> JsonResult<T> fail(BizException bizexception) {
        return fail(bizexception.getCode(), bizexception.getMsg());
    }

    /**
     * 失败--数据
     *
     * @param data data
     * @param <T>  t
     * @return JsonResult
     */
    public static <T> JsonResult<T> fail(T data) {
        JsonResult<T> ret = JsonResult.fail();
        ret.setData(data);
        return ret;
    }

    @Override
    public String toString() {
        return "code=" + code + " message=" + msg + " data=" + data;
    }

}
