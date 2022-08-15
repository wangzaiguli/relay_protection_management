package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:07
 */
@Data
public class RoleUpdateInput extends RoleSaveInput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

}