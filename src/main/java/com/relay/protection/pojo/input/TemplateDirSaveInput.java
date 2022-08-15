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
 * @date 2022/7/4 14:07
 */
@Data
public class TemplateDirSaveInput extends BaseInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "父节点")
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @NotNull
    private String name;

}
