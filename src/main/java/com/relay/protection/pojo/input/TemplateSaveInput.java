package com.relay.protection.pojo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/4 14:07
 */
@Data
public class TemplateSaveInput extends TemplateDirSaveInput implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "文件id")
    @NotNull
    private Long fileId;

}
