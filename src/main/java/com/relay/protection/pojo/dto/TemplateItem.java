package com.relay.protection.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/8/9 16:45
 */
@Data
public class TemplateItem {

    @ApiModelProperty(value = "排序")
    @NotNull
    private Integer order;

    @ApiModelProperty(value = "标题")
    @NotNull
    private String title;

    @ApiModelProperty(value = "代码")
    @NotNull
    private String code;

    @ApiModelProperty(value = "数据项")
    @NotNull
    private List<TemplateConfig> list;

}
