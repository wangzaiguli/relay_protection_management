package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:07
 */
@Data
public class UserRoleUpdateInput extends BaseInput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "角色主键集合")
    @NotNull
    private List<Long> roleIdList;

}
