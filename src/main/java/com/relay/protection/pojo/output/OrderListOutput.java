package com.relay.protection.pojo.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderListOutput extends BaseOutput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "最新操作人")
    private String userName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime updateTime;

}