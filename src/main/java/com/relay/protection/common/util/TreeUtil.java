package com.relay.protection.common.util;

import com.relay.protection.pojo.BaseTree;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/13 16:32
 */
public class TreeUtil {

    /**
     * 根据map构建树
     *
     * @param tree 树节点
     * @param map  map
     * @param <T>  type
     */
    public static <T extends BaseTree> void find(T tree, Map<Long, List<T>> map) {
        Long id = tree.getId();
        if (!map.containsKey(id)) {
            return;
        }
        List<T> list = map.get(id);
        for (T t : list) {
            tree.getChild().add(t);
            find(t, map);
        }
    }

    /**
     * 根据map构建树
     *
     * @param tree 树节点
     * @param map  map
     * @param <T>  type
     */
    public static <T extends BaseTree> void toMap(T tree, String value, Map<Long, String> map) {
        if (tree == null) {
            return;
        }
        Long id = tree.getId();
        value = value + File.separator + tree.getName();
        map.put(id, value);
        List<T> child = tree.getChild();
        if (child != null) {
            for (T t : child) {
                toMap(t, value, map);
            }
        }
    }


    /**
     * 获取id子节点的主键
     *
     * @param tree   树节点
     * @param idList 子节点主键
     * @param id     节点
     * @param flag   是否id的子节点
     * @param <T>    type
     */
    public static <T extends BaseTree> void getChildIds(T tree, List<Long> idList, long id, boolean flag) {
        if (tree == null) {
            return;
        }
        if (flag || tree.getId() == id) {
            idList.add(tree.getId());
            flag = true;
        }
        List<T> child = tree.getChild();
        if (ObjectUtils.isEmpty(child)) {
            return;
        }
        for (T deptOutput : child) {
            getChildIds(deptOutput, idList, id, flag);
        }
    }

    /**
     * 获取id子节点的主键
     *
     * @param tree 树节点
     * @param map  子节点
     * @param id   节点
     * @param flag 是否id的子节点
     * @param <T>  type
     */
    public static <T extends BaseTree> void getChilds(T tree, Map<Long, T> map, long id, boolean flag) {
        if (tree == null) {
            return;
        }
        if (flag || tree.getId() == id) {
            map.put(tree.getId(), tree);
            flag = true;
        }
        List<T> child = tree.getChild();
        if (ObjectUtils.isEmpty(child)) {
            return;
        }
        for (T deptOutput : child) {
            getChilds(deptOutput, map, id, flag);
        }
    }


    /**
     * 模糊匹配名称获取id子节点的主键
     *
     * @param tree   树节点
     * @param idList 子节点主键
     * @param name   目录名称
     * @param flag   是否id的子节点
     * @param <T>    type
     */
    public static <T extends BaseTree> void getChildIdsLikeName(T tree, List<Long> idList, String name, boolean flag) {
        if (tree == null) {
            return;
        }
        if (flag || tree.getName().contains(name)) {
            idList.add(tree.getId());
            flag = true;
        }
        List<T> child = tree.getChild();
        if (ObjectUtils.isEmpty(child)) {
            return;
        }
        for (T deptOutput : child) {
            getChildIdsLikeName(deptOutput, idList, name, flag);
        }
    }


}
