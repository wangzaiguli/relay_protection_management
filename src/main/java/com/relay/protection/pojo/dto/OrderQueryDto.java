package com.relay.protection.pojo.dto;

import com.relay.protection.common.enums.FlowEnum;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/15 16:21
 */
@Data
public class OrderQueryDto {

    private Long userId;

    private String apply = FlowEnum.APPLY.getKey();

    private String calc = FlowEnum.CALC.getKey();

    private String check = FlowEnum.CHECK.getKey();

    private String debug = FlowEnum.DEBUG.getKey();

    private String verify = FlowEnum.VERIFY.getKey();

    private String approval = FlowEnum.APPROVAL.getKey();

    private String execute = FlowEnum.EXECUTE.getKey();

    private String executeSuc = FlowEnum.EXECUTE_SUC.getKey();

    private String debugSuc = FlowEnum.DEBUG_SUC.getKey();

    private String debugFail = FlowEnum.DEBUG_FAIL.getKey();

    private String reject = FlowEnum.REJECT.getKey();

    private String calcReject = FlowEnum.CALC_REJECT.getKey();

    private String cancel = FlowEnum.CANCEL.getKey();

    private String save = FlowEnum.SAVE.getKey();

    private String status;

    private String name;

    private String code;


}
