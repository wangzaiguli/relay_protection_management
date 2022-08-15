package com.relay.protection.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 15:43
 */
@Data
public class UserAuth  implements Serializable {
    private static final long serialVersionUID = -1L;

    private Long roleId;

    private String name;

    private String code;

    private String perms;
}
