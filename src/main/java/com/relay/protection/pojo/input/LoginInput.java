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
 * @date 2022/5/26 21:04
 */
@Data
public class LoginInput extends BaseInput  implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "姓名")
    @NotNull
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotNull
    private String password;
}
