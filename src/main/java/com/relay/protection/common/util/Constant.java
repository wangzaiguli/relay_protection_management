package com.relay.protection.common.util;

import com.relay.protection.common.enums.FlowEnum;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/10 16:39
 */
@UtilityClass
public class Constant {

    public static final long ROOT_ID = -1;

    public static final long ROOT_ORDER_ID = 1;

    public static final String ROOT_DIR_NAME = "目录";

    public static final String SLASH = "/";

    public static final String APPLY_LIST = "apply_list";
    public static final String APPROVAL_LIST = "approval_list";
    public static final String CALC_LIST = "calc_list";
    public static final String CHECK_LIST = "check_list";
    public static final String EXECUTE_LIST = "execute_list";
    public static final String VERIFY_LIST = "verify_list";

    public static final String[] PERMS_ARR = {APPLY_LIST, APPROVAL_LIST, CALC_LIST, CHECK_LIST, EXECUTE_LIST, VERIFY_LIST};
    public static final String[] FLOW_ARR = {FlowEnum.APPLY.getKey(), FlowEnum.APPROVAL.getKey(), FlowEnum.CALC.getKey(), FlowEnum.CHECK.getKey(), FlowEnum.EXECUTE.getKey(), FlowEnum.VERIFY.getKey()};


    public static final Map<String, String> TEMPLATE_MAP = new LinkedHashMap<String, String>() {{
        put("整定信息", "a");
        put("设备参数", "a");
        put("整定原则", "a");
        put("计算参数", "a");
    }};

    public static final String PUSHPLUS_PATH = "http://www.pushplus.plus/send";

    public static final String STR_SEPARATE = ",";

    public static final String MSG_STR = "{\n" +
            "    \"时间\": \"今日%s-%s\",\n" +
            "    \"教室\": \"%s\",\n" +
            "    \"科目\": \"%s\"\n" +
            "}";

    public static final String TITLE_STR = "星华教育\"%s\"上课通知";

    public static final String CONTENT = "content";

    public static final String TITLE = "title";

    public static final String TOKEN = "token";

    public static final String TEMPLATE = "template";

    public static final String TEMPLATE_TYPE = "json";


}
