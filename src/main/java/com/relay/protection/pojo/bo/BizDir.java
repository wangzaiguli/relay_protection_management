package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("BIZ_DIR")
public class BizDir extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "NAME")
    @ApiModelProperty(value = "目录名称")
    private String name;

    @TableField(value = "VIEW")
    @ApiModelProperty(value = "是否展示（定值单目录不展示）")
    private String view;


}