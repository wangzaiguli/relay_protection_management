package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_ROLE")
public class SysRole extends BaseEntity  implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "CODE")
    private String code;

}