package com.relay.protection.pojo.output;

import com.relay.protection.pojo.BaseTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/4 14:07
 */
@Data
public class DeptOutput extends BaseOutput implements BaseTree<DeptOutput>, Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "子节点")
    private List<DeptOutput> child = new ArrayList<>();

    /**
     * Get the value of child.
     *
     * @return The value of child.
     */
    @Override
    public List<DeptOutput> getChild() {
        return child;
    }
}
