package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.init.MenuInit;
import com.relay.protection.common.init.RoleInit;
import com.relay.protection.mapper.SysMenuMapper;
import com.relay.protection.mapper.SysRoleMapper;
import com.relay.protection.mapper.SysRoleMethodMapper;
import com.relay.protection.pojo.bo.SysRole;
import com.relay.protection.pojo.bo.SysRoleMethod;
import com.relay.protection.pojo.dto.UserAuth;
import com.relay.protection.pojo.input.RoleQueryInput;
import com.relay.protection.pojo.input.RoleSaveInput;
import com.relay.protection.pojo.input.RoleUpdateInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.MenuOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.pojo.output.RoleOutput;
import com.relay.protection.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:00
 */
@Service
public class SysRoleService extends BaseService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMethodMapper sysRoleMethodMapper;

    @Autowired
    private RoleInit roleInit;

    /**
     * 新增 注册
     *
     * @param vo 数据
     * @return 1
     */
    public JsonResult<Long> save(RoleSaveInput vo) {
        SysRole sysRole = dtoConverter.roleSaveVo2Bo(vo);
        sysRoleMapper.insert(sysRole);
        List<Long> methodIdList = MenuInit.getMethodIdList(vo.getMenuTree());
        for (Long methodId : methodIdList) {
            sysRoleMethodMapper.insert(new SysRoleMethod(sysRole.getId(), methodId));
        }
        roleInit.init();
        return JsonResult.success(sysRole.getId());
    }

    /**
     * 修改
     *
     * @param vo 数据
     * @return 1
     */
    @Transactional
    public JsonResult<Long> updete(RoleUpdateInput vo) {
        SysRole sysRole = dtoConverter.roleUpdateVo2Bo(vo);
        sysRoleMapper.updateById(sysRole);
        List<Long> methodIdList = MenuInit.getMethodIdList(vo.getMenuTree());
        LambdaQueryWrapper<SysRoleMethod> qw = new LambdaQueryWrapper<>();
        qw.eq(SysRoleMethod::getRoleId, vo.getId());
        sysRoleMethodMapper.delete(qw);
        for (Long methodId : methodIdList) {
            sysRoleMethodMapper.insert(new SysRoleMethod(sysRole.getId(), methodId));
        }
        roleInit.init();
        return JsonResult.success(vo.getId());
    }

    /**
     * 查询详情
     *
     * @param id 主键
     * @return 详情
     */
    public JsonResult<RoleOutput> query(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        RoleOutput userShowVo = dtoConverter.roleBo2Vo(sysRole);
        if (!ObjectUtils.isEmpty(userShowVo)) {
            List<UserAuth> menuList = sysMenuMapper.getRoleMenuList(id);
            MenuOutput tree = MenuInit.getRoot();
            MenuInit.setTree(tree, menuList.stream().map(UserAuth::getCode).collect(Collectors.toSet()));
            userShowVo.setMenuTree(tree);
            return JsonResult.success(userShowVo);
        }
        throw new BizException(JsonReturnCode.DATA_NOT_EXIST);
    }

    /**
     * 查询列表
     *
     * @param vo 查询条件
     * @return data
     */
    public JsonResult<PageOutput<RoleOutput>> queryList(RoleQueryInput vo) {
        LambdaQueryWrapper<SysRole> qw = new LambdaQueryWrapper<>();
        PageOutput.Node<SysRole, RoleOutput> node = new PageOutput.Node<SysRole, RoleOutput>()
                .setFun(dtoConverter::roleBo2Vo).add(RoleOutput::setStatus);
        if (Boolean.TRUE.equals(vo.getEnable())) {
            node.setPage(sysRoleMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), qw));
        } else {
            node.setList(sysRoleMapper.selectList(qw));
        }
        return JsonResult.success(PageOutput.page(node));

    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 1
     */
    public JsonResult<Integer> delete(Long id) {
        int count = sysRoleMapper.deleteById(id);
        LambdaQueryWrapper<SysRoleMethod> qw = new LambdaQueryWrapper<>();
        qw.eq(SysRoleMethod::getRoleId , id);
        sysRoleMethodMapper.delete(qw);
        roleInit.init();
        return JsonResult.success(count);
    }


    /**
     * 查询详情
     *
     * @return 详情
     */
    public JsonResult<MenuOutput> queryRoleMenu() {
        MenuOutput tree = MenuInit.getRoot();
        return JsonResult.success(tree);
    }

}
