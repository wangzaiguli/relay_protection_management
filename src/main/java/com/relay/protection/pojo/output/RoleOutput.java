package com.relay.protection.pojo.output;

import com.relay.protection.common.init.MenuInit;
import com.relay.protection.common.init.SelectInit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:07
 */
@Data
public class RoleOutput extends BaseOutput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态")
    private String statusValue;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "权限树")
    private MenuOutput menuTree;

    public RoleOutput setStatus () {
        this.statusValue = SelectInit.SELECT_CONVTER_MAP.get(SelectInit.FLOW).get(this.status);
        return this;
    }

}
