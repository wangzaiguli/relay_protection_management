package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.TreeUtil;
import com.relay.protection.mapper.SysDeptMapper;
import com.relay.protection.mapper.SysUserMapper;
import com.relay.protection.pojo.bo.SysDept;
import com.relay.protection.pojo.bo.SysUser;
import com.relay.protection.pojo.input.DeptSaveInput;
import com.relay.protection.pojo.input.DeptUpdateInput;
import com.relay.protection.pojo.output.DeptOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/4 14:01
 */
@Service
@Slf4j
public class SysDeptService extends BaseService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public JsonResult<DeptOutput> queryList() {
        Map<Long, List<DeptOutput>> map = sysDeptMapper.selectList(null)
                .stream().map(dtoConverter::deptBo2Output).collect(Collectors.groupingBy(DeptOutput::getParentId, Collectors.toList()));
        DeptOutput root = new DeptOutput();
        root.setId(-1L);
        TreeUtil.find(root, map);
        return JsonResult.success(root);
    }

    public JsonResult<Long> save(DeptSaveInput vo) {
        // 父节点存在
        LambdaQueryWrapper<SysDept> qw = new LambdaQueryWrapper<>();
        qw.eq(SysDept::getId, vo.getParentId());
        Integer count = sysDeptMapper.selectCount(qw);
        if (count == null || count == 0) {
            throw new BizException(JsonReturnCode.DEPT_PARENT_ERROR);
        }
        qw.clear();
        // 兄弟节点没有重复的名称和代码
        qw.eq(SysDept::getParentId, vo.getParentId());
        qw.and(item -> item.eq(SysDept::getName, vo.getName()).or().eq(SysDept::getCode, vo.getCode()));
        count = sysDeptMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DEPT_EXIST_ERROR);
        }
        // 保存;
        SysDept sysDept = dtoConverter.deptSave2Bo(vo);
        sysDeptMapper.insert(sysDept);
        return JsonResult.success(sysDept.getId());
    }

    public JsonResult<Long> update(DeptUpdateInput vo) {
        LambdaQueryWrapper<SysDept> qw = new LambdaQueryWrapper<>();
        // 兄弟节点没有重复的名称和代码
        qw.ne(SysDept::getId, vo.getId()).eq(SysDept::getParentId, vo.getParentId());
        qw.and(item -> item.eq(SysDept::getName, vo.getName()).or().eq(SysDept::getCode, vo.getCode()));
        Integer count = sysDeptMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DEPT_EXIST_ERROR);
        }
        // 修改
        SysDept sysDept = dtoConverter.deptUpdate2Bo(vo);
        sysDeptMapper.updateById(sysDept);
        return JsonResult.success(sysDept.getId());
    }

    public JsonResult<Integer> delete(Long id) {
        DeptOutput data = queryList().getData();
        List<Long> idList = new ArrayList<>();
        TreeUtil.getChildIds(data, idList, id, false);
        if (ObjectUtils.isEmpty(idList)) {
            return JsonResult.success(0);
        }
        LambdaQueryWrapper<SysUser> userQw = new LambdaQueryWrapper<>();
        userQw.in(SysUser::getDeptId, idList);

        Integer count = sysUserMapper.selectCount(userQw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DEPT_HAVE_USER_ERROR);
        }
        // 删除
        return JsonResult.success(sysDeptMapper.deleteBatchIds(idList));
    }


}
