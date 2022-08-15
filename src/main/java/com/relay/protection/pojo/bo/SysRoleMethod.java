package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName("SYS_ROLE_METHOD")
public class SysRoleMethod  implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId(type = IdType.AUTO, value = "ID")
    @ApiModelProperty(value = "主键")
    private Long id;

    @TableField(value = "ROLE_ID")
    private Long roleId;

    @TableField(value = "METHOD_ID")
    private Long methodId;

    public SysRoleMethod(Long roleId, Long methodId) {
        this.roleId = roleId;
        this.methodId = methodId;
    }
}