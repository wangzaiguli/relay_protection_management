package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:15
 */
@Data
public class OrderQueryInput extends PageInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "名称，手动输入，模糊匹配")
    private String name;

    @ApiModelProperty(value = "代码，手动输入，模糊匹配")
    private String code;

    @ApiModelProperty(value = "状态，下拉框选取")
    private String status;

}
