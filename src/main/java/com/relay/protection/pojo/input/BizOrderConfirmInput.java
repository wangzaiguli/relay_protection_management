package com.relay.protection.pojo.input;

import com.relay.protection.pojo.bo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BizOrderConfirmInput extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "文件")
    private List<Long> idList;

}