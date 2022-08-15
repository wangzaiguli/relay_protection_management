package com.relay.protection.pojo.input;

import com.relay.protection.pojo.output.BaseOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/4 14:07
 */
@Data
public class DeptSaveInput extends BaseInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "父节点")
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "部门名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "代码")
    @NotNull
    private String code;

}
