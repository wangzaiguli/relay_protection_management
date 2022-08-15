package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:12
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId(type = IdType.AUTO, value = "ID")
    @ApiModelProperty(value = "主键")
    private Long id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

}
