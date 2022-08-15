package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.SentUtil;
import com.relay.protection.mapper.SysRoleMapper;
import com.relay.protection.mapper.SysUserMapper;
import com.relay.protection.mapper.SysUserRoleMapper;
import com.relay.protection.pojo.bo.SysUser;
import com.relay.protection.pojo.bo.SysUserRole;
import com.relay.protection.pojo.input.UpdatePasswordInput;
import com.relay.protection.pojo.input.UserQueryInput;
import com.relay.protection.pojo.input.UserSaveInput;
import com.relay.protection.pojo.input.UserUpdateInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.pojo.output.UserOutput;
import com.relay.protection.service.common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@Slf4j
@Service
public class SysUserService extends BaseService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 新增 注册
     *
     * @param vo 数据
     * @return 1
     */
    @Transactional
    public JsonResult<Long> save(UserSaveInput vo) {
        LambdaQueryWrapper<SysUser> qw = new LambdaQueryWrapper<>();
        qw.eq(SysUser::getUserName, vo.getUserName());
        Integer count = sysUserMapper.selectCount(qw);
        if (count > 0) {
            throw new BizException(JsonReturnCode.NAME_EXIST_ERROR);
        }
        SysUser sysUser = dtoConverter.userSaveVo2Bo(vo);
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        sysUserMapper.insert(sysUser);
        for (Long roleId : vo.getRoleIdList()) {
            sysUserRoleMapper.insert(new SysUserRole(sysUser.getId(), roleId));
        }
        return JsonResult.success(sysUser.getId());
    }

    /**
     * 修改密码
     *
     * @param vo 数据
     * @return 1
     */
    public JsonResult<Integer> updetePassword(UpdatePasswordInput vo) {
        SysUser user = getUserInfo().getUser();
        if (!user.getPassword().equals(vo.getOld())) {
            throw new BizException(JsonReturnCode.PASSWORD_ERROR);
        }
        if (!vo.getNewFirst().equals(vo.getNewSecond())) {
            throw new BizException(JsonReturnCode.PASSWORD_NEW_ERROR);
        }
        LambdaUpdateChainWrapper<SysUser> uw = new LambdaUpdateChainWrapper<>(sysUserMapper);
        uw.set(SysUser::getPassword, passwordEncoder.encode(vo.getNewFirst()));
        uw.eq(SysUser::getId, user.getId());
        uw.update();
        return JsonResult.success(1);
    }

    /**
     * 修改签章
     *
     * @param signature 数据
     * @return 1
     */
    public JsonResult<Integer> updeteSignature(Long signature) {
        SysUser user = getUserInfo().getUser();
        LambdaUpdateChainWrapper<SysUser> uw = new LambdaUpdateChainWrapper<>(sysUserMapper);
        uw.set(SysUser::getSignature, signature);
        uw.eq(SysUser::getId, user.getId());
        uw.update();
        return JsonResult.success(1);
    }

    /**
     * 修改
     *
     * @param vo 数据
     * @return 1
     */
    @Transactional
    public JsonResult<Long> updete(UserUpdateInput vo) {
        SysUser sysUser = dtoConverter.userUpdateVo2Bo(vo);
        sysUserMapper.updateById(sysUser);
        LambdaQueryWrapper<SysUserRole> qw = new LambdaQueryWrapper<>();
        qw.eq(SysUserRole::getUserId, vo.getId());
        sysUserRoleMapper.delete(qw);
        List<Long> roleIdList = vo.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysUserRoleMapper.insert(new SysUserRole(vo.getId(), roleId));
        }
        return JsonResult.success(vo.getId());
    }

    /**
     * 查询详情
     *
     * @param id 主键
     * @return 详情
     */
    public JsonResult<UserOutput> query(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        UserOutput output = dtoConverter.userBo2Vo(sysUser);
        if (!ObjectUtils.isEmpty(output)) {
            LambdaQueryWrapper<SysUserRole> qw = new LambdaQueryWrapper<>();
            qw.eq(SysUserRole::getUserId, id);
            qw.select(SysUserRole::getRoleId);
            List<Long> list = sysUserRoleMapper.selectList(qw).stream()
                    .map(SysUserRole::getRoleId).collect(Collectors.toList());
            output.setRoleIdList(list);
            return JsonResult.success(output);
        }
        throw new BizException(JsonReturnCode.DATA_NOT_EXIST);
    }

    /**
     * 查询列表
     *
     * @param vo 查询条件
     * @return data
     */
    public JsonResult<PageOutput<UserOutput>> queryList(UserQueryInput vo) {
        LambdaQueryWrapper<SysUser> qw = new LambdaQueryWrapper<>();
        PageOutput.Node<SysUser, UserOutput> node = new PageOutput.Node<SysUser, UserOutput>()
                .setFun(dtoConverter::userBo2Vo);
        if (Boolean.TRUE.equals(vo.getEnable())) {
            node.setPage(sysUserMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), qw));
        } else {
            node.setList(sysUserMapper.selectList(qw));
        }
        return JsonResult.success(PageOutput.page(node));
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 1
     */
    @Transactional
    public JsonResult<Integer> delete(Long id) {
        LambdaQueryWrapper<SysUserRole> qw = new LambdaQueryWrapper<>();
        qw.eq(SysUserRole::getUserId, id);
        sysUserRoleMapper.delete(qw);
        return JsonResult.success(sysUserMapper.deleteById(id));
    }

//    @Transactional
//    public JsonResult<Integer> updeteUserRole(UserRoleUpdateInput vo) {
//        LambdaQueryWrapper<SysUserRole> qw = new LambdaQueryWrapper<>();
//        qw.eq(SysUserRole::getUserId, vo.getUserId());
//        sysUserRoleMapper.delete(qw);
//        List<Long> roleIdList = vo.getRoleIdList();
//        for (Long roleId : roleIdList) {
//            sysUserRoleMapper.insert(new SysUserRole(vo.getUserId(), roleId));
//        }
//        return JsonResult.success(roleIdList.size());
//    }
//
//    public JsonResult<PageOutput<RoleOutput>> queryUserRole(Long userId) {
//        PageOutput.Node<SysRole, RoleOutput> node = new PageOutput.Node<SysRole, RoleOutput>()
//                .setFun(dtoConverter::roleBo2Vo)
//                .setList(sysRoleMapper.getUserRoleList(userId));
//        return JsonResult.success(PageOutput.page(node));
//    }

    public JsonResult<Integer> saveToken(String token) {
        new LambdaUpdateChainWrapper<>(sysUserMapper)
                .set(SysUser::getPushToken, token)
                .eq(SysUser::getId, getUserInfo().getUser().getId())
                .update();
        return JsonResult.success(1);
    }

    public JsonResult<String> testPush() {
        SysUser sysUser = sysUserMapper.selectById(getUserInfo().getUser().getId());
        String content = String.format(SentUtil.TEST_CONTENT, sysUser.getUserName());
        String sent = SentUtil.sent(sysUser.getPushToken(), SentUtil.TEST_TITLE, content);
        log.info("test push----------------" + sent);
        return JsonResult.success(sent);
    }

}
