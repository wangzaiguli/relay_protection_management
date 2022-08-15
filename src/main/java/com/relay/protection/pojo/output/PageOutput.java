package com.relay.protection.pojo.output;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/30 11:56
 */
@Data
public class PageOutput<T> extends BaseOutput implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "数据")
    private List<T> list;

    @ApiModelProperty(value = "当前满足条件总行数")
    private long total;


    @ApiModelProperty(value = "获取每页显示条数")
    private long size;


    @ApiModelProperty(value = "当前页")
    private long current;

    @ApiModelProperty(value = "当前分页总页数")
    private long pages;

    public static <V, T> PageOutput<T> page(Node<V, T> node) {
        PageOutput<T> info = new PageOutput<>();
        List<UnaryOperator<T>> convertFunList = node.getConvertFunList();
        IPage<V> page = node.getPage();
        if (page != null) {
            info.setList(page.getRecords().stream().map(node.getFun()).collect(Collectors.toList()));
            info.setPages(page.getPages());
            info.setTotal(page.getTotal());
            info.setCurrent(page.getCurrent());
            info.setSize(page.getSize());
            if (convertFunList != null) {
                for (T t : info.getList()) {
                    for (UnaryOperator<T> convertFun : convertFunList) {
                        convertFun.apply(t);
                    }
                }
            }
            return info;
        }
        info.setPages(1);
        info.setCurrent(1);
        List<V> list = node.getList();
        if (ObjectUtils.isEmpty(list)) {
            info.setList(Collections.emptyList());
            info.setTotal(0);
            info.setSize(0);
            return info;
        }
        info.setList(list.stream().map(node.getFun()).collect(Collectors.toList()));
        if (convertFunList != null) {
            for (T t : info.getList()) {
                for (UnaryOperator<T> convertFun : convertFunList) {
                    convertFun.apply(t);
                }
            }
        }
        info.setTotal(list.size());
        info.setSize(list.size());
        return info;
    }

    @Data
    @Accessors(chain = true)
    public static class Node<V, T> {
        private List<V> list;
        private IPage<V> page;
        private Function<V, T> fun;
        private List<UnaryOperator<T>> convertFunList;

        public Node<V, T> add(UnaryOperator<T> unaryOperator) {
            if (convertFunList == null) {
                convertFunList = new ArrayList<>();
            }
            convertFunList.add(unaryOperator);
            return this;
        }

    }

}
