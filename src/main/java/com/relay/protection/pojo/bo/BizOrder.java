package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("BIZ_ORDER")
public class BizOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableField(value = "NAME")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField(value = "CODE")
    @ApiModelProperty(value = "代码")
    private String code;

    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "流程")
    @TableField(exist = false)
    private List<BizOrderFlow> flowList;

}