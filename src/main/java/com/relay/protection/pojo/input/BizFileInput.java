package com.relay.protection.pojo.input;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("BIZ_FILE")
public class BizFileInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableField(value = "ID")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    @TableField(value = "FLOW_ID")
    @ApiModelProperty(value = "父节点")
    private Long flowId;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "NAME")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField(value = "PATH")
    @ApiModelProperty(value = "路径")
    private String path;


}