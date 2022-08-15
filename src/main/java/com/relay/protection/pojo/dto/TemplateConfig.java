package com.relay.protection.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/8/9 16:22
 */
@Data
public class TemplateConfig {

    @ExcelProperty(index = 1)
    @ApiModelProperty(value = "名称")
    @NotNull
    private String name;

    @ExcelProperty(index = 2)
    @ApiModelProperty(value = "代码")
    @NotNull
    private String code;

    @ExcelProperty(index = 3)
    @ApiModelProperty(value = "值")
    @NotNull
    private String value;
}
