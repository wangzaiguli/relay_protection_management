package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BizOrderTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "NAME")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField(value = "VALUE")
    @ApiModelProperty(value = "模板值")
    private String value;

    @TableField(value = "FILE_ID")
    @ApiModelProperty(value = "文件id")
    private Long fileId;

    @TableField(value = "TYPE")
    @ApiModelProperty(value = "类型 I:模板 M:菜单")
    private String type;

}