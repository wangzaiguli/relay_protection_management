package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_METHOD")
public class SysMethod extends BaseEntity  implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableField(value = "PARENT_ID")
    private Long parentId;

    /**
     * 菜单名
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 代码
     */
    @TableField(value = "CODE")
    private String code;

    /**
     * 路由地址
     */
    @TableField(value = "PATH")
    private String path;

    /**
     * 请求方式
     */
    @TableField(value = "METHOD_TYPE")
    private String methodType;

    /**
     * 权限标识
     */
    @TableField(value = "PERMS")
    private String perms;

}