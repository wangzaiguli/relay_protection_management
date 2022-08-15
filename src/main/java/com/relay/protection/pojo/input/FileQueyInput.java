package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/4 14:07
 */
@Data
public class FileQueyInput extends PageInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "目录名称")
    private String dirName;

    @ApiModelProperty(value = "父id集合")
    private Set<Long> parentIds;

    @ApiModelProperty(value = "上传时间开始")
    private LocalDateTime start;

    @ApiModelProperty(value = "上传时间结束")
    private LocalDateTime end;

}
