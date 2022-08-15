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
public enum MenuTypeEnum implements BaseEnum {

    /**
     * 菜单类型
     */
    M("M", "目录"),
    I("I", "接口");

    private String key;
    private String value;

    MenuTypeEnum(String key, String value) {
        this.value = value;
        this.key = key;
    }

}
