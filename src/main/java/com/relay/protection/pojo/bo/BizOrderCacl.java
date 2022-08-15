package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BizOrderCacl extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "VALUE")
    @ApiModelProperty(value = "计算值")
    private String value;

}