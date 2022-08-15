package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/30 11:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInput extends BaseInput  implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "是否启用分页")
    @NotNull
    private Boolean enable;

    @ApiModelProperty(value = "页容量")
    private Integer pageSize;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

}
