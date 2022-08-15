package com.relay.protection.pojo.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 15:52
 */
@Data
public class LoginOutput extends BaseOutput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "userName")
    private String userName;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "目录树")
    private MenuOutput menuTree;

}
