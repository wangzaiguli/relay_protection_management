package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * .
 *
 * @author zhuHx
 * @version 1.0
 * @date 2022/5/27 15:23
 */
@Data
public class UpdatePasswordInput extends BaseInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "旧密码")
    @NotNull
    private String old;

    @ApiModelProperty(value = "新密码1")
    @NotNull
    private String newFirst;

    @ApiModelProperty(value = "新密码2")
    @NotNull
    private String newSecond;

}
