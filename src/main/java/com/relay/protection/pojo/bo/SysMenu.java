package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_MENU")
public class SysMenu extends BaseEntity  implements Serializable {
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
     * 菜单图标
     */
    @TableField(value = "ICON")
    private String icon;
}