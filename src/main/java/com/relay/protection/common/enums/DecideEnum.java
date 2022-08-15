package com.relay.protection.common.enums;

import lombok.Getter;

/**
 * .
 *
 * @author zhuHx
 * @version 1.0
 * @date 2022/5/26 21:05
 */
@Getter
public enum DecideEnum implements BaseEnum {

    /**
     * 用户状态
     */
    Y("Y", "展示，使用"),
    N("N", "隐藏，停用");

    private String key;
    private String value;

    DecideEnum(String key, String value) {
        this.value = value;
        this.key = key;
    }

}
