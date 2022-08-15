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
 * @date 2022/7/1 17:07
 */
@Data
public class MenuInput extends BaseInput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long roleId;

    @ApiModelProperty(value = "目录结构")
    @NotNull
    private MenuOutput tree;

}
