package com.relay.protection.pojo.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInfoOutput extends BaseOutput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "流程")
    private List<OrderFlowOutput> flowList;


    @Data
    public static class OrderFlowOutput extends BaseOutput implements Serializable {

        private static final long serialVersionUID = -1L;

        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "状态")
        private String status;

        @ApiModelProperty(value = "操作时间")
        private LocalDateTime updateTime;

        @ApiModelProperty(value = "操作人")
        private String userName;

        @ApiModelProperty(value = "驳回原因")
        private String remark;

        @ApiModelProperty(value = "文件")
        private List<FileOutput> fileList;

    }

    @Data
    public static class FileOutput implements Serializable {

        private static final long serialVersionUID = -1L;

        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "名称")
        private String name;

    }

}