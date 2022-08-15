package com.relay.protection.common.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/6 15:45
 */
@Getter
public enum FlowEnum implements BaseEnum {

    /**
     *
     */
    APPLY("a", "申请"),
    CALC("j", "待计算"),
    CHECK("c", "待复核"),
    DEBUG("d", "待调试"),
    VERIFY("v", "待审核"),
    APPROVAL("p", "待批准"),
    EXECUTE("e", "待执行"),
    EXECUTE_SUC("w", "执行成功"),
    DEBUG_SUC("x", "调试成功"),
    DEBUG_FAIL("f", "调试失败"),
    REJECT("r", "驳回"),
    CALC_REJECT("h", "计算驳回"),
    CANCEL("m", "取消"),
    SAVE("s", "保存"),
    ;

    private String key;
    private String value;

    FlowEnum(String key, String value) {
        this.value = value;
        this.key = key;
    }

}
