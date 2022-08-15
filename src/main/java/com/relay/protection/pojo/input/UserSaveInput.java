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
 * @date 2022/6/30 11:15
 */
@Data
public class UserSaveInput extends BaseInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "用户姓名")
    @NotNull
    private String userName;

    @ApiModelProperty(value = "用户编号")
    private String code;

    @ApiModelProperty(value = "密码")
    @NotNull
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户性别")
    private String sex;

    @ApiModelProperty(value = "部门")
    private String deptId;

    @ApiModelProperty(value = "角色主键集合")
    @NotNull
    private List<Long> roleIdList;

}
