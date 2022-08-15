package com.relay.protection.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.relay.protection.common.enums.MenuTypeEnum;
import com.relay.protection.common.enums.TemplateConfigEnum;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.Constant;
import com.relay.protection.common.util.ExcelUtil;
import com.relay.protection.common.util.FileUtil;
import com.relay.protection.common.util.TreeUtil;
import com.relay.protection.mapper.BizFileMapper;
import com.relay.protection.mapper.BizOrderCaclMapper;
import com.relay.protection.mapper.BizOrderTemplateMapper;
import com.relay.protection.pojo.bo.BizFile;
import com.relay.protection.pojo.bo.BizOrderCacl;
import com.relay.protection.pojo.bo.BizOrderTemplate;
import com.relay.protection.pojo.dto.TemplateConfig;
import com.relay.protection.pojo.dto.TemplateItem;
import com.relay.protection.pojo.input.CalcInput;
import com.relay.protection.pojo.input.TemplateDirSaveInput;
import com.relay.protection.pojo.input.TemplateDirUpdateInput;
import com.relay.protection.pojo.input.TemplateSaveInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.OrderTemplateOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.relay.protection.common.util.SpelUtil.contains;
import static com.relay.protection.common.util.SpelUtil.executeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 17:00
 */
@Service
public class OrderTemplateService extends BaseService {

    @Autowired
    private BizOrderTemplateMapper bizOrderTemplateMapper;
    @Autowired
    private BizOrderCaclMapper bizOrderCaclMapper;

    @Autowired
    private BizFileMapper bizFileMapper;

    public JsonResult<OrderTemplateOutput> queryList() {
        LambdaQueryWrapper<BizOrderTemplate> qw = new LambdaQueryWrapper<>();
        Map<Long, List<OrderTemplateOutput>> map = bizOrderTemplateMapper.selectList(qw)
                .stream().map(dtoConverter::templateBo2Output).collect(Collectors.groupingBy(OrderTemplateOutput::getParentId, Collectors.toList()));
        OrderTemplateOutput root = new OrderTemplateOutput();
        root.setId(Constant.ROOT_ID);
        root.setName(Constant.ROOT_DIR_NAME);
        TreeUtil.find(root, map);
        return JsonResult.success(root);
    }

    @Transactional
    public JsonResult<Integer> saveDir(TemplateDirSaveInput vo, String type) {
        return saveFile(dtoConverter.templateSave2Template(vo), type);
    }

    @Transactional
    public JsonResult<Integer> saveFile(TemplateSaveInput vo, String type) {
        // 父节点存在
        LambdaQueryWrapper<BizOrderTemplate> qw = new LambdaQueryWrapper<>();
        if (vo.getParentId() != -1) {
            qw.eq(BizOrderTemplate::getId, vo.getParentId());
            Integer count = bizOrderTemplateMapper.selectCount(qw);
            if (count == null || count == 0) {
                throw new BizException(JsonReturnCode.DIR_PARENT_ERROR);
            }
        }
        qw.clear();
        // 兄弟节点没有重复的名称和代码
        qw.eq(BizOrderTemplate::getParentId, vo.getParentId());
        qw.and(item -> item.eq(BizOrderTemplate::getName, vo.getName()));
        Integer count = bizOrderTemplateMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DIR_EXIST_ERROR);
        }

        BizOrderTemplate orderTemplate = dtoConverter.templateSave2Bo(vo);
        orderTemplate.setType(type);

        // 保存文件，读取模板sheet2
        if (!ObjectUtils.isEmpty(vo.getFileId())) {
            String path = FileUtil.CLASS_PATH + bizFileMapper.selectById(vo.getFileId()).getPath();
            List<TemplateItem> templateItemList = ExcelUtil.getTemplateItemList(path);
            // 计算公式
            List<TemplateItem> caclList = templateItemList.stream()
                    .filter(item -> TemplateConfigEnum.CACL_PARAM.getKey().equals(item.getCode()))
                    .collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(caclList)) {
                orderTemplate.setValue(JSON.toJSONString(caclList.get(0)));
            }
            bizOrderTemplateMapper.insert(orderTemplate);

            List<TemplateItem> otherList = templateItemList.stream()
                    .filter(item -> !TemplateConfigEnum.CACL_PARAM.getKey().equals(item.getCode()))
                    .collect(Collectors.toList());
            // 保存参数
            BizOrderCacl cacl = new BizOrderCacl();
            cacl.setParentId(orderTemplate.getId());
            cacl.setValue(JSON.toJSONString(otherList));
            return JsonResult.success(bizOrderCaclMapper.insert(cacl));
        } else {
            return JsonResult.success(bizOrderTemplateMapper.insert(orderTemplate));
        }

    }

    public JsonResult<Integer> update(TemplateDirUpdateInput vo) {
        LambdaQueryWrapper<BizOrderTemplate> qw = new LambdaQueryWrapper<>();
        // 兄弟节点没有重复的名称和代码
        qw.ne(BizOrderTemplate::getId, vo.getId())
                .eq(BizOrderTemplate::getParentId, vo.getParentId())
                .eq(BizOrderTemplate::getName, vo.getName());
        Integer count = bizOrderTemplateMapper.selectCount(qw);
        if (count != null && count > 0) {
            throw new BizException(JsonReturnCode.DIR_EXIST_ERROR);
        }
        // 修改
        BizOrderTemplate template = dtoConverter.templateUpdate2Bo(vo);
        return JsonResult.success(bizOrderTemplateMapper.updateById(template));
    }

    public JsonResult<Integer> deleteDir(Long id) {
        if (id == -1) {
            throw new BizException(JsonReturnCode.DIR_NOT_DEL_ROOT);
        }
        OrderTemplateOutput data = queryList().getData();

        Map<Long, OrderTemplateOutput> map = new HashMap<>();
        TreeUtil.getChilds(data, map, id, false);
        if (ObjectUtils.isEmpty(map)) {
            return JsonResult.success(0);
        }

        // 有模板的目录不可删除
        for (Map.Entry<Long, OrderTemplateOutput> entry : map.entrySet()) {
            String type = entry.getValue().getType();
            if (MenuTypeEnum.I.getKey().equals(type)) {
                throw new BizException(JsonReturnCode.DIR_HAVE_DATA_ERROR);
            }
        }
        // 删除
        return JsonResult.success(bizOrderTemplateMapper.deleteBatchIds(map.keySet()));
    }

    /**
     * 获取历史数据
     *
     * @param id
     * @return
     */
    public JsonResult<PageOutput<List<TemplateItem>>> caclList(Long id) {
        LambdaQueryWrapper<BizOrderCacl> qw = new LambdaQueryWrapper<>();
        qw.eq(BizOrderCacl::getParentId, id);
        qw.orderByDesc(BizOrderCacl::getId);
        PageOutput.Node<BizOrderCacl, List<TemplateItem>> node = new PageOutput.Node<BizOrderCacl, List<TemplateItem>>()
                .setFun(item -> JSON.parseArray(item.getValue(), TemplateItem.class));
        node.setPage(bizOrderCaclMapper.selectPage(new Page<>(1, 10), qw));
        return JsonResult.success(PageOutput.page(node));
    }

    public JsonResult<Void> cacl(CalcInput input) {
        Long id = input.getId();
        BizOrderTemplate orderTemplate = bizOrderTemplateMapper.selectById(id);
        if (orderTemplate == null) {
            throw new BizException(JsonReturnCode.DATA_ERROR);
        }
        String value = orderTemplate.getValue();
        // 整理参数
        Map<String, Object> map = new HashMap<>();
        for (TemplateItem item : input.getList()) {
            List<TemplateConfig> subItems = item.getList();
            for (TemplateConfig sub : subItems) {
                map.put(sub.getCode(), contains(value, sub.getCode()) ? Double.parseDouble(sub.getValue()) : sub.getValue());
            }
        }
        // spel计算
        for (TemplateConfig config : JSON.parseObject(value, TemplateItem.class).getList()) {
            executeMap(map, config.getValue());
        }
        BizFile bizFile = bizFileMapper.selectById(orderTemplate.getFileId());
        String templatePath = FileUtil.CLASS_PATH + bizFile.getPath();
        String cachePath = FileUtil.getCachePath(bizFile.getPath());
        // 填充文件
        ExcelUtil.excelWrite(templatePath, cachePath, map);
        // 下载
        FileUtil.outFile(response(), new File(cachePath));
        return JsonResult.success();
    }

    @Transactional
    public JsonResult<Integer> deleteFile(Long id) {
        if (id == -1) {
            throw new BizException(JsonReturnCode.DIR_NOT_DEL_ROOT);
        }
        BizOrderTemplate orderTemplate = bizOrderTemplateMapper.selectById(id);
        if (orderTemplate == null) {
            throw new BizException(JsonReturnCode.DATA_ERROR);
        }
        if (!MenuTypeEnum.I.getKey().equals(orderTemplate.getType())) {
            throw new BizException(JsonReturnCode.DIR_NOT_FILE);
        }

        // 删除文件，删除file表
        Long fileId = orderTemplate.getFileId();
        bizFileMapper.deleteById(fileId);

        LambdaQueryWrapper<BizOrderCacl> qw = new LambdaQueryWrapper<>();
        qw.eq(BizOrderCacl::getParentId, orderTemplate.getId());
        bizOrderCaclMapper.delete(qw);

        // 删除
        return JsonResult.success(bizOrderTemplateMapper.deleteById(id));
    }

}
