package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("BIZ_ORDER_FLOW")
public class BizOrderFlow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "文件")
    @TableField(exist = false)
    private List<BizFile> fileList;

}