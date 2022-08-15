package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.relay.protection.common.enums.DecideEnum;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.Constant;
import com.relay.protection.common.util.TreeUtil;
import com.relay.protection.mapper.BizFileMapper;
import com.relay.protection.mapper.BizDirMapper;
import com.relay.protection.mapper.SysUserMapper;
import com.relay.protection.pojo.bo.BizDir;
import com.relay.protection.pojo.bo.BizFile;
import com.relay.protection.pojo.input.DirSaveInput;
import com.relay.protection.pojo.input.DirUpdateInput;
import com.relay.protection.pojo.output.DirOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * @date 2022/7/1 17:00
 */
@Service
public class DirService extends BaseService {

    @Autowired
    private BizDirMapper bizDirMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BizFileMapper bizFileMapper;

    public JsonResult<DirOutput> queryList(boolean show) {
        LambdaQueryWrapper<BizDir> qw = new LambdaQueryWrapper<>();
        if (show) {
            qw.eq(BizDir::getView, DecideEnum.Y.getKey());
        }

        Map<Long, List<DirOutput>> map = bizDirMapper.selectList(qw)
                .stream().map(dtoConverter::dirBo2Output).collect(Collectors.groupingBy(DirOutput::getParentId, Collectors.toList()));
        DirOutput root = new DirOutput();
        root.setId(Constant.ROOT_ID);
        root.setName(Constant.ROOT_DIR_NAME);
        TreeUtil.find(root, map);
        return JsonResult.success(root);
    }

    @Transactional
    public JsonResult<Integer> save(DirSaveInput vo) {
        // 父节点存在
        LambdaQueryWrapper<BizDir> qw = new LambdaQueryWrapper<>();
        if (vo.getParentId() != -1) {
            qw.eq(BizDir::getId, vo.getParentId());
            Integer count = bizDirMapper.selectCount(qw);
            if (count == null || count == 0) {
                throw new BizException(JsonReturnCode.DIR_PARENT_ERROR);
            }
        }
        qw.clear();
        // 兄弟节点没有重复的名称和代码
        qw.eq(BizDir::getParentId, vo.getParentId());
        qw.and(item -> item.eq(BizDir::getName, vo.getName()));
        Integer  count = bizDirMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DIR_EXIST_ERROR);
        }
        // 保存
        BizDir bizDir = dtoConverter.dirSave2Bo(vo);
        bizDir.setView(DecideEnum.Y.getKey());
        return JsonResult.success(bizDirMapper.insert(bizDir));
    }

    public JsonResult<Integer> update(DirUpdateInput vo) {
        LambdaQueryWrapper<BizDir> qw = new LambdaQueryWrapper<>();
        // 兄弟节点没有重复的名称和代码
        qw.ne(BizDir::getId, vo.getId())
                .eq(BizDir::getParentId, vo.getParentId())
                .eq(BizDir::getName, vo.getName());
        Integer count = bizDirMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DIR_EXIST_ERROR);
        }
        // 修改
        BizDir bizDir = dtoConverter.dirUpdate2Bo(vo);
        bizDir.setView(DecideEnum.Y.getKey());
        return JsonResult.success(bizDirMapper.updateById(bizDir));
    }

    public JsonResult<Integer> delete(Long id) {
        if (id == -1) {
            throw new BizException(JsonReturnCode.DIR_NOT_DEL_ROOT);
        }
        DirOutput data = queryList(true).getData();
        List<Long> idList = new ArrayList<>();
        TreeUtil.getChildIds(data, idList, id, false);
        if (ObjectUtils.isEmpty(idList)) {
            return JsonResult.success(0);
        }
        LambdaQueryWrapper<BizFile> fileQw = new LambdaQueryWrapper<>();
        fileQw.in(BizFile :: getParentId, idList);
        Integer count = bizFileMapper.selectCount(fileQw);

        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DIR_HAVE_DATA_ERROR);
        }
        // 删除
        return JsonResult.success(bizDirMapper.deleteBatchIds(idList));
    }


}
