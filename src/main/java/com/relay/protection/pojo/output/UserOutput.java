package com.relay.protection.pojo.output;

import com.relay.protection.common.init.SelectInit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/30 11:15
 */
@Data
public class UserOutput extends BaseOutput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotNull
    private String userName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String sexValue;

    @ApiModelProperty(value = "部门")
    private String deptId;

    @ApiModelProperty(value = "角色主键集合")
    @NotNull
    private List<Long> roleIdList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
