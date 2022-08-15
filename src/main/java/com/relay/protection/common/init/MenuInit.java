package com.relay.protection.common.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.relay.protection.common.enums.MenuTypeEnum;
import com.relay.protection.common.util.Constant;
import com.relay.protection.common.util.StrUtil;
import com.relay.protection.mapper.SysMenuMapper;
import com.relay.protection.mapper.SysMethodMapper;
import com.relay.protection.pojo.bo.SysMenu;
import com.relay.protection.pojo.bo.SysMethod;
import com.relay.protection.pojo.output.MenuOutput;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 13:56
 */
@Data
@Slf4j
@Component
public class MenuInit {

    @Autowired
    private SysMethodMapper sysMethodMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Value("${mydata.noModel}")
    private String noModel;

    @Value("${mydata.noFlow}")
    private String noFlow;

    public static final MenuOutput ROOT = new MenuOutput(-1L);

    @PostConstruct
    public void init() {
        log.info("init -----------  menu");

        LambdaQueryWrapper<SysMenu> menuQw = new LambdaQueryWrapper<>();
        List<SysMenu> sysMenus = sysMenuMapper.selectList(menuQw);
        Map<Long, List<SysMenu>> menuMap = sysMenus.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId, Collectors.toList()));

        LambdaQueryWrapper<SysMethod> methodQw = new LambdaQueryWrapper<>();
        List<SysMethod> sysMethodList = sysMethodMapper.selectList(methodQw);
        Map<Long, List<SysMethod>> methodMap = sysMethodList.stream()
                .collect(Collectors.groupingBy(SysMethod::getParentId, Collectors.toList()));

        // 构建root
        find(ROOT, menuMap, methodMap);

        // 删除没有配置的节点
        List<String> list = StrUtil.str2List(noModel, Constant.STR_SEPARATE);
        list.addAll(StrUtil.str2List(noFlow, Constant.STR_SEPARATE));
        if (!list.isEmpty()) {
            del(ROOT, list);
        }
    }

    private void del(MenuOutput node, List<String> list) {
        if (node == null || ObjectUtils.isEmpty(node.getChild())) {
            return;
        }
        List<MenuOutput> child = node.getChild();
        for (int i = child.size() - 1; i >= 0; i--) {
            if (list.contains(child.get(i).getCode())) {
                child.remove(i);
            } else {
                del(child.get(i), list);
            }
        }
    }

    private void find(MenuOutput node, Map<Long, List<SysMenu>> childMap, Map<Long, List<SysMethod>> methodMap) {
        List<SysMethod> sysMethodList = methodMap.get(node.getId());
        if (sysMethodList != null) {
            for (SysMethod sysMethod : sysMethodList) {
                node.getChild().add(toNode(sysMethod));
            }
        }

        List<SysMenu> sysMenuList = childMap.get(node.getId());
        if (ObjectUtils.isEmpty(sysMenuList)) {
            return;
        }
        for (SysMenu sysMenu : sysMenuList) {
            MenuOutput no = toNode(sysMenu);
            node.getChild().add(no);
            find(no, childMap, methodMap);
        }
    }

    private MenuOutput toNode(SysMenu sysMenu) {
        MenuOutput node = new MenuOutput(sysMenu.getId());
        node.setCode(sysMenu.getCode());
        node.setIcon(sysMenu.getIcon());
        node.setName(sysMenu.getName());
        node.setType(MenuTypeEnum.M.getKey());
        return node;
    }

    private MenuOutput toNode(SysMethod sysMethod) {
        MenuOutput node = new MenuOutput(sysMethod.getId());
        node.setCode(sysMethod.getCode());
        node.setName(sysMethod.getName());
        node.setPath(sysMethod.getPath());
        node.setType(MenuTypeEnum.I.getKey());
        node.setMethodType(sysMethod.getMethodType());
        return node;
    }

    public static MenuOutput getRoot() {
        return (MenuOutput) ROOT.clone();
    }

    public static boolean setTree(MenuOutput tree, Set<String> set) {
        List<MenuOutput> child = tree.getChild();
        boolean flag = set.contains(tree.getCode());
        if (ObjectUtils.isEmpty(child)) {
            tree.setShow(flag);
            return flag;
        }
        for (MenuOutput menuTree : child) {
            flag = setTree(menuTree, set) || flag;
        }
        tree.setShow(flag);
        return flag;
    }

    public static List<Long> getMethodIdList(MenuOutput tree) {
        List<Long> list = new ArrayList<>();
        getMethodIdList(tree, list);
        return list;
    }

    private static void getMethodIdList(MenuOutput tree, List<Long> list) {

        if (ObjectUtils.isEmpty(tree)) {
            return;
        }
        if (tree.getType().equals(MenuTypeEnum.I.getKey())) {
            list.add(tree.getId());
        }
        List<MenuOutput> child = tree.getChild();
        if (ObjectUtils.isEmpty(child)) {
            return;
        }
        for (MenuOutput menuTree : child) {
            getMethodIdList(menuTree, list);
        }
    }

}
