package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderFlowInput extends BaseInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "流程主键")
    private Long flowId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "驳回原因")
    private String remark;

    @ApiModelProperty(value = "文件")
    private List<Long> fileIdList;

}