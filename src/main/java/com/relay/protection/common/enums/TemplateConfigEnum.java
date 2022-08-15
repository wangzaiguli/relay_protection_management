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
public enum TemplateConfigEnum implements BaseEnum {

    /**
     * 模板配置
     */
    SET_INFO("SET_INFO", "整定信息", 1),
    DEVICE_PARAM("DEVICE_PARAM", "设备参数", 2),
    SET_TENET("SET_TENET", "整定原则", 3),
    CACL_PARAM("CACL_PARAM", "计算参数", 4);


    private String key;
    private String value;
    private int order;

    TemplateConfigEnum(String key, String value, int order) {
        this.value = value;
        this.key = key;
        this.order = order;
    }

}
