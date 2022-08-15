package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.FileUtil;
import com.relay.protection.common.util.TreeUtil;
import com.relay.protection.mapper.BizDirMapper;
import com.relay.protection.mapper.BizFileMapper;
import com.relay.protection.pojo.bo.BizFile;
import com.relay.protection.pojo.input.FileQueyInput;
import com.relay.protection.pojo.input.FileSaveInput;
import com.relay.protection.pojo.output.DirOutput;
import com.relay.protection.pojo.output.FileOutput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/18 14:50
 */
@Service
@Slf4j
public class FileService extends BaseService {

    @Autowired
    private BizFileMapper bizFileMapper;

    @Autowired
    private DirService dirService;

    @Autowired
    private BizDirMapper bizDirMapper;

    public JsonResult<PageOutput<FileOutput>> queryList(FileQueyInput vo) {
        DirOutput dirOutput = dirService.queryList(false).getData();
        List<Long> ids = new ArrayList<>();
        if (!ObjectUtils.isEmpty(vo.getDirName())) {
            TreeUtil.getChildIdsLikeName(dirOutput, ids, vo.getDirName(), false);
            if (ObjectUtils.isEmpty(ids)) {
                return JsonResult.success();
            }
        }
        LambdaQueryWrapper<BizFile> qw = new LambdaQueryWrapper<>();
        qw.isNotNull(BizFile::getParentId);
        qw.orderByDesc(BizFile::getUpdateTime);
        if (!ObjectUtils.isEmpty(vo.getName())) {
            qw.like(BizFile::getName, vo.getName());
        }
        if (!ObjectUtils.isEmpty(vo.getStart())) {
            qw.ge(BizFile::getCreateTime, vo.getStart());
        }
        if (!ObjectUtils.isEmpty(vo.getEnd())) {
            qw.le(BizFile::getCreateTime, vo.getEnd());
        }
        if (!ObjectUtils.isEmpty(vo.getParentIds()) ) {
            Set<Long> parentIds = vo.getParentIds();
            parentIds.retainAll(ids);
            qw.in(BizFile::getParentId, parentIds);
        } else if (!ObjectUtils.isEmpty(ids)) {
            qw.in(BizFile::getParentId, ids);
        }
        PageOutput.Node<BizFile, FileOutput> node = new PageOutput.Node<BizFile, FileOutput>()
                .setFun(dtoConverter::fileBo2Vo);
        if (Boolean.TRUE.equals(vo.getEnable())) {
            node.setPage(bizFileMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), qw));
        } else {
            node.setList(bizFileMapper.selectList(qw));
        }
        PageOutput<FileOutput> page = PageOutput.page(node);
        Map<Long, String> map = new HashMap<>();
        for (DirOutput output : dirOutput.getChild()) {
            TreeUtil.toMap(output, "", map);
        }
        List<FileOutput> list = page.getList();
        for (FileOutput fileOutput : list) {
            fileOutput.setDirName(map.get(fileOutput.getParentId()));
        }
        return JsonResult.success(page);
    }

    /**
     * 新增
     *
     * @param vo 数据
     * @return 1
     */
    public JsonResult<Integer> save(FileSaveInput vo) {
        LambdaUpdateWrapper<BizFile> uw = new LambdaUpdateWrapper<>();
        uw.set(BizFile::getParentId, vo.getParentId())
                .set(BizFile::getRemark, vo.getRemark())
                .eq(BizFile::getId, vo.getId());
        return JsonResult.success(bizFileMapper.update(null, uw));
    }

    /**
     * 删除
     *
     * @param id 数据
     * @return 1
     */
    public JsonResult<Integer> delete(Long id) {
        return JsonResult.success(bizFileMapper.deleteById(id));
    }

    public JsonResult<Long> upload(MultipartFile file) {
        String fileName = FileUtil.makeFileName(file.getOriginalFilename());
        try {
            String path = FileUtil.getPath(filePath());
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            File dest = new File(f, fileName);
            file.transferTo(dest);
            BizFile bizFile = new BizFile();
            bizFile.setName(file.getOriginalFilename());
            bizFile.setPath(path.substring(FileUtil.CLASS_PATH.length()) + fileName);
            bizFileMapper.insert(bizFile);
            return JsonResult.success(bizFile.getId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_UPLOAD_ERROR);
        }
    }

    public JsonResult<Void> download(Long id) {
        BizFile bizFile = bizFileMapper.selectById(id);
        if (bizFile == null) {
            throw new BizException(JsonReturnCode.FILE_NOT_EXIST_ERROR);
        }
        FileUtil.outFile(response(), new File(FileUtil.CLASS_PATH + bizFile.getPath()));
        return JsonResult.success();
    }

}
