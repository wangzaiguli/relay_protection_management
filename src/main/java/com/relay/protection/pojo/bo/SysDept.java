package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_DEPT")
public class SysDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "NAME")
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @TableField(value = "CODE")
    @ApiModelProperty(value = "代码")
    private String code;

    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态")
    private String status;


}