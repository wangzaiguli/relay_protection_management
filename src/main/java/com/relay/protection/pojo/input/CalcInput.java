package com.relay.protection.pojo.input;

import com.relay.protection.pojo.dto.TemplateItem;
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
 * @date 2022/8/10 17:12
 */
@Data
public class CalcInput extends BaseInput  implements Serializable {


    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "输入的参数")
    @NotNull
    private List<TemplateItem> list;

}
