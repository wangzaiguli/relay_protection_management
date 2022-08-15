package com.relay.protection.service.common;

import com.relay.protection.common.init.SelectInit;
import com.relay.protection.common.util.FileUtil;
import com.relay.protection.pojo.output.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/13 11:10
 */
@Service
@Slf4j
public class CommonService extends BaseService {

    @Value("${logging.path}")
    private String logPath;

    @Autowired
    private SelectInit selectInit;

    public JsonResult<Map<String, List<SelectInit.Node>>> getSelect(Set<String> set) {
        Map<String, List<SelectInit.Node>> map = SelectInit.SELECT_NODE_MAP.entrySet().stream()
                .filter(item -> set.contains(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return JsonResult.success(map);
    }

    public JsonResult<Void> getLog() {
        File file = new File(logPath + "/spring.log");
        FileUtil.outFile(response(), file);
        return JsonResult.success();
    }


}
