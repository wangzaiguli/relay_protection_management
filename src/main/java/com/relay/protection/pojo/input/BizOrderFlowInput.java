package com.relay.protection.pojo.input;

import com.relay.protection.pojo.bo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BizOrderFlowInput extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "文件")
    private List<Long> idList;

}