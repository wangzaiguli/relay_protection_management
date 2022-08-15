package com.relay.protection.pojo.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/30 11:15
 */
@Data
public class FileOutput extends BaseOutput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "目录")
    private String dirName;

    @ApiModelProperty(value = "描述")
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
