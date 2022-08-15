package com.relay.protection.pojo.output;

import com.relay.protection.pojo.BaseTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 15:53
 */

@Data
@NoArgsConstructor
public class DirOutput extends BaseOutput implements BaseTree<DirOutput>, Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "子节点")
    private List<DirOutput> child = new ArrayList<>();

    public DirOutput(Long id) {
        this.id = id;
    }


}
