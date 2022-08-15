package com.relay.protection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.relay.protection.common.enums.DecideEnum;
import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.Constant;
import com.relay.protection.mapper.BizDirMapper;
import com.relay.protection.mapper.BizFileMapper;
import com.relay.protection.mapper.BizOrderFlowMapper;
import com.relay.protection.mapper.BizOrderMapper;
import com.relay.protection.pojo.bo.BizDir;
import com.relay.protection.pojo.bo.BizFile;
import com.relay.protection.pojo.bo.BizOrder;
import com.relay.protection.pojo.bo.BizOrderFlow;
import com.relay.protection.pojo.dto.OrderQueryDto;
import com.relay.protection.pojo.dto.UserAuth;
import com.relay.protection.pojo.input.OrderFlowInput;
import com.relay.protection.pojo.input.OrderQueryInput;
import com.relay.protection.pojo.input.PageInput;
import com.relay.protection.pojo.output.JsonResult;
import com.relay.protection.pojo.output.OrderInfoOutput;
import com.relay.protection.pojo.output.OrderListOutput;
import com.relay.protection.pojo.output.PageOutput;
import com.relay.protection.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.relay.protection.common.util.Constant.FLOW_ARR;
import static com.relay.protection.common.util.Constant.PERMS_ARR;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/15 14:37
 */
@Service
public class OrderService extends BaseService {

    @Autowired
    private BizOrderMapper bizOrderMapper;

    @Autowired
    private BizOrderFlowMapper bizOrderFlowMapper;

    @Autowired
    private BizFileMapper bizFileMapper;

    @Autowired
    private BizDirMapper bizDirMapper;

    private final Map<String, BiFunction<IPage<BizOrder>, OrderQueryDto, IPage<BizOrder>>> queryPageMap = new HashMap<>();
    private final Map<String, Function<OrderQueryDto, List<BizOrder>>> queryListMap = new HashMap<>();
    private final Map<String, List<String>> typeMap = new HashMap<>();

    @PostConstruct
    private void init() {
        queryPageMap.put(FlowEnum.APPLY.getKey(), bizOrderMapper::applyQuery);
        queryPageMap.put(FlowEnum.CALC.getKey(), bizOrderMapper::calcQuery);
        queryPageMap.put(FlowEnum.CHECK.getKey(), bizOrderMapper::checkQuery);
        queryPageMap.put(FlowEnum.VERIFY.getKey(), bizOrderMapper::verifyQuery);
        queryPageMap.put(FlowEnum.APPROVAL.getKey(), bizOrderMapper::approvalQuery);
        queryPageMap.put(FlowEnum.EXECUTE.getKey(), bizOrderMapper::executeQuery);

        queryListMap.put(FlowEnum.APPLY.getKey(), bizOrderMapper::applyQuery);
        queryListMap.put(FlowEnum.CALC.getKey(), bizOrderMapper::calcQuery);
        queryListMap.put(FlowEnum.CHECK.getKey(), bizOrderMapper::checkQuery);
        queryListMap.put(FlowEnum.VERIFY.getKey(), bizOrderMapper::verifyQuery);
        queryListMap.put(FlowEnum.APPROVAL.getKey(), bizOrderMapper::approvalQuery);
        queryListMap.put(FlowEnum.EXECUTE.getKey(), bizOrderMapper::executeQuery);

        typeMap.put(FlowEnum.APPLY.getKey(), Arrays.asList(FlowEnum.SAVE.getKey(), FlowEnum.CALC_REJECT.getKey()));
        typeMap.put(FlowEnum.CALC.getKey(), Arrays.asList(FlowEnum.CALC.getKey(), FlowEnum.REJECT.getKey(), FlowEnum.DEBUG_FAIL.getKey()));
        typeMap.put(FlowEnum.CHECK.getKey(), Arrays.asList(FlowEnum.CHECK.getKey(), FlowEnum.DEBUG_SUC.getKey()));
        typeMap.put(FlowEnum.VERIFY.getKey(), Collections.singletonList(FlowEnum.VERIFY.getKey()));
        typeMap.put(FlowEnum.APPROVAL.getKey(), Collections.singletonList(FlowEnum.APPROVAL.getKey()));
        typeMap.put(FlowEnum.EXECUTE_SUC.getKey(), Collections.singletonList(FlowEnum.EXECUTE.getKey()));
        typeMap.put(FlowEnum.DEBUG_SUC.getKey(), Collections.singletonList(FlowEnum.DEBUG.getKey()));
        typeMap.put(FlowEnum.EXECUTE.getKey(), Arrays.asList(FlowEnum.EXECUTE.getKey(), FlowEnum.DEBUG.getKey()));
    }

    public JsonResult<PageOutput<OrderListOutput>> queryHomeList() {
        int count = 8;
        PageInput vo = new PageInput(true, count, 1);
        List<OrderListOutput> all = new ArrayList<>();
        Set<String> permSet = getUserInfo().getPermissions().stream().map(UserAuth::getPerms).collect(Collectors.toSet());
        for (int i = 0; i < PERMS_ARR.length; i++) {
            if (permSet.contains(PERMS_ARR[i])) {
                all.addAll(queryList(vo, FLOW_ARR[i]).getData().getList());
            }
        }
        all.sort(Comparator.comparing(OrderListOutput::getUpdateTime).reversed());
        if (all.size() > count) {
            all = all.subList(0, count);
        }
        PageOutput.Node<OrderListOutput, OrderListOutput> node = new PageOutput.Node<>();
        node.setList(all);
        return JsonResult.success(PageOutput.page(node));
    }

    public JsonResult<PageOutput<OrderListOutput>> queryList(PageInput vo, String type) {
        OrderQueryDto queryDto = new OrderQueryDto();
        queryDto.setUserId(getUserInfo().getUser().getId());
        PageOutput.Node<BizOrder, OrderListOutput> node = new PageOutput.Node<BizOrder, OrderListOutput>()
                .setFun(dtoConverter::orderListBo2Output);
        if (Boolean.TRUE.equals(vo.getEnable())) {
            node.setPage(queryPageMap.get(type).apply(new Page<>(vo.getPageNo(), vo.getPageSize()), queryDto));
        } else {
            node.setList(queryListMap.get(type).apply(queryDto));
        }
        return JsonResult.success(PageOutput.page(node));
    }

    public JsonResult<PageOutput<OrderListOutput>> queryAllList(OrderQueryInput vo) {
        OrderQueryDto queryDto = new OrderQueryDto();
        queryDto.setName(vo.getName());
        queryDto.setCode(vo.getCode());
        queryDto.setStatus(vo.getStatus());
        PageOutput.Node<BizOrder, OrderListOutput> node = new PageOutput.Node<BizOrder, OrderListOutput>()
                .setFun(dtoConverter::orderListBo2Output);
        if (Boolean.TRUE.equals(vo.getEnable())) {
            node.setPage(bizOrderMapper.allQuery(new Page<>(vo.getPageNo(), vo.getPageSize()), queryDto));
        } else {
            node.setList(bizOrderMapper.allQuery(queryDto));
        }
        return JsonResult.success(PageOutput.page(node));
    }

    public JsonResult<OrderInfoOutput> queryInfo(Long id) {
        return JsonResult.success(dtoConverter.orderInfoBo2Output(bizOrderMapper.queryInfo(id)));
    }

    @Transactional
    public JsonResult<Integer> save(OrderFlowInput input) {
        if (input.getId() == null) {
            return JsonResult.success(save(input, FlowEnum.SAVE.getKey()));
        }
        return JsonResult.success(addFlow(input, FlowEnum.SAVE.getKey(), typeMap.get(FlowEnum.APPLY.getKey())));
    }

    @Transactional
    public JsonResult<Integer> applyConfirm(OrderFlowInput input) {
        if (input.getId() == null) {
            return JsonResult.success(save(input, flowInit.getNext(FlowEnum.APPLY.getKey())));
        }
        return JsonResult.success(addFlow(input, flowInit.getNext(FlowEnum.APPLY.getKey()), typeMap.get(FlowEnum.APPLY.getKey())));
    }

    @Transactional
    public JsonResult<Integer> cancel(Long id) {
        BizOrder bizOrder = bizOrderMapper.selectById(id);
        if (bizOrder == null || !FlowEnum.CALC_REJECT.getKey().equals(bizOrder.getStatus())) {
            throw new BizException(JsonReturnCode.DATA_ERROR);
        }
        LambdaUpdateWrapper<BizOrder> uw = new LambdaUpdateWrapper<>();
        uw.set(BizOrder::getStatus, FlowEnum.CANCEL.getKey());
        uw.eq(BizOrder::getId, id);
        bizOrderMapper.update(null, uw);
        BizOrderFlow orderFlow = new BizOrderFlow();
        orderFlow.setParentId(id);
        orderFlow.setStatus(FlowEnum.CANCEL.getKey());
        int insert = bizOrderFlowMapper.insert(orderFlow);
        return JsonResult.success(insert);
    }

    @Transactional
    public JsonResult<Integer> confirm(OrderFlowInput input, String type) {
        return JsonResult.success(addFlow(input, flowInit.getNext(type), typeMap.get(type)));
    }

    @Transactional
    public JsonResult<Integer> reject(OrderFlowInput input, String type) {
        return JsonResult.success(addFlow(input, FlowEnum.REJECT.getKey(), typeMap.get(type)));
    }

    @Transactional
    public JsonResult<Integer> next(OrderFlowInput input, String next, String type) {
        return JsonResult.success(addFlow(input, next, typeMap.get(type)));
    }

    @Transactional
    public JsonResult<Integer> executeSuc(OrderFlowInput input, String type) {
        JsonResult.success(addFlow(input, FlowEnum.EXECUTE_SUC.getKey(), typeMap.get(type)));

        LambdaQueryWrapper<BizOrderFlow> flowQw = new LambdaQueryWrapper<>();
        flowQw.eq(BizOrderFlow::getParentId, input.getId());
        flowQw.select(BizOrderFlow::getId);
        List<Long> idList = bizOrderFlowMapper.selectList(flowQw).stream()
                .map(BizOrderFlow::getId).collect(Collectors.toList());
        BizOrder bizOrder = bizOrderMapper.selectById(input.getId());
        // 整理文件
        arrangeFile(bizOrder.getName(), idList);
        return JsonResult.success(1);
    }

    private int addFlow(OrderFlowInput input, String type, List<String> list) {
        Long id = input.getId();
        if (id == null) {
            throw new BizException(JsonReturnCode.DATA_ERROR);
        }
        BizOrder db = bizOrderMapper.selectById(id);
        if (db == null || !list.contains(db.getStatus())) {
            throw new BizException(JsonReturnCode.DATA_ERROR);
        }

        // 若是在调试中驳回，则是调试失败
        if (FlowEnum.DEBUG.getKey().equals(db.getStatus()) && type.equals(FlowEnum.REJECT.getKey())) {
            type = FlowEnum.DEBUG_FAIL.getKey();
        }

        db.setStatus(type);
        db.setUpdateTime(null);
        db.setUpdateUser(null);
        bizOrderMapper.updateById(db);
        BizOrderFlow orderFlow = new BizOrderFlow();
        orderFlow.setParentId(db.getId());
        orderFlow.setStatus(type);
        orderFlow.setRemark(input.getRemark());
        bizOrderFlowMapper.insert(orderFlow);
        if (!ObjectUtils.isEmpty(input.getFlowId())) {
            Long oldFlowId = input.getFlowId();
            LambdaQueryWrapper<BizFile> qw = new LambdaQueryWrapper<>();
            qw.eq(BizFile::getFlowId, oldFlowId);
            bizFileMapper.delete(qw);
        }
        if (!ObjectUtils.isEmpty(input.getFileIdList())) {
            addFlowId(input.getFileIdList(), orderFlow.getId());
        }
        return 1;
    }

    private int save(OrderFlowInput input, String type) {
        BizOrder bizOrder = dtoConverter.applySave2Bo(input);
        bizOrder.setStatus(type);
        bizOrderMapper.insert(bizOrder);
        BizOrderFlow orderFlow = new BizOrderFlow();
        orderFlow.setParentId(bizOrder.getId());
        orderFlow.setStatus(type);
        bizOrderFlowMapper.insert(orderFlow);
        addFlowId(input.getFileIdList(), orderFlow.getId());
        return 1;
    }

    private void addFlowId(List<Long> fileIdList, Long flowId) {
        if (ObjectUtils.isEmpty(fileIdList)) {
            return;
        }
        for (Long fileId : fileIdList) {
            LambdaUpdateWrapper<BizFile> uw = new LambdaUpdateWrapper<>();
            uw.set(BizFile::getFlowId, flowId);
            uw.eq(BizFile::getId, fileId);
            bizFileMapper.update(null, uw);
        }
    }

    /**
     * 整理文件  定值单 整合到文件管理系统
     *
     * @param name   定值单名称
     * @param idList 文件id集合
     */
    private void arrangeFile(String name, List<Long> idList) {
        BizDir bizDir = new BizDir();
        bizDir.setName(name);
        bizDir.setParentId(Constant.ROOT_ORDER_ID);
        bizDir.setView(DecideEnum.N.getKey());
        bizDirMapper.insert(bizDir);

        LambdaUpdateWrapper<BizFile> uw = new LambdaUpdateWrapper<>();
        uw.in(BizFile::getFlowId, idList);
        uw.set(BizFile::getParentId, bizDir.getId());
        bizFileMapper.update(null, uw);
    }

}
