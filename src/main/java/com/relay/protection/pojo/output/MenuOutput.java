package com.relay.protection.pojo.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class MenuOutput extends BaseOutput implements Cloneable, Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "请求方式")
    private String methodType;

    @ApiModelProperty(value = "类别(M:目录 I:接口)")
    private String type;

    @ApiModelProperty(value = "图标(只有目录有)")
    private String icon;

    @ApiModelProperty(value = "是否展示")
    private Boolean show = false;

    @ApiModelProperty(value = "子节点")
    private List<MenuOutput> child = new ArrayList<>();

    public MenuOutput(Long id) {
        this.id = id;
    }

    @Override
    public Object clone() {
        MenuOutput node = new MenuOutput();
        node.setId(id);
        node.setPath(path);
        node.setType(type);
        node.setIcon(icon);
        node.setName(name);
        node.setCode(code);
        node.setMethodType(methodType);
        for (MenuOutput no : this.child) {
            node.child.add((MenuOutput) no.clone());
        }
        return node;
    }

}
