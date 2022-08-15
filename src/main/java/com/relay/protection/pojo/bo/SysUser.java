package com.relay.protection.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_USER")
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "用户姓名")
    @TableField(value = "USER_NAME")
    private String userName;

    @ApiModelProperty(value = "用户编号")
    @TableField(value = "CODE")
    private String code;

    @ApiModelProperty(value = "密码")
    @TableField(value = "PASSWORD")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @TableField(value = "EMAIL")
    private String email;

    @ApiModelProperty(value = "手机号")
    @TableField(value = "PHONE")
    private String phone;

    @ApiModelProperty(value = "性别")
    @TableField(value = "SEX")
    private String sex;

    @ApiModelProperty(value = "部门")
    @TableField(value = "DEPT_ID")
    private String deptId;

    @ApiModelProperty(value = "签章")
    @TableField(value = "SIGNATURE")
    private Long signature;

    @ApiModelProperty(value = "推送token")
    @TableField(value = "PUSH_TOKEN")
    private String pushToken;
}
