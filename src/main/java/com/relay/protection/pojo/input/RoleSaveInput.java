package com.relay.protection.pojo.input;

import com.relay.protection.pojo.output.MenuOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:05
 */
@Data
public class RoleSaveInput extends BaseInput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "代码")
    @NotNull
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单树， 配置的权限， show=true")
    private MenuOutput menuTree;

}
