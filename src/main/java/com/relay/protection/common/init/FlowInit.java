package com.relay.protection.common.init;

import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.common.util.Constant;
import com.relay.protection.common.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/6 16:02
 */
@Data
@Slf4j
@Component
public class FlowInit {

    private Node node;

    private Map<String, Node> map = new HashMap<>();

    @Value("${mydata.flow}")
    private String flowstr;

    @PostConstruct
    public void init() {
        log.info("init -----------  flow");
        List<String> list = StrUtil.str2List(flowstr, Constant.STR_SEPARATE);
        Node no;
        try {
            this.node = new Node(FlowEnum.valueOf(list.get(0).toUpperCase()));
            log.info("init -----------  {}", node.item.getValue());
            no = this.node;
            for (int i = 1; i < list.size(); i++) {
                no.setNext(new Node(FlowEnum.valueOf(list.get(i).toUpperCase())));
                no = no.next;
            }
        } catch (Exception e) {
            log.error("initflow", e);
            throw new BizException(JsonReturnCode.FLOW_CONFIG_ERROR);
        }

        no = this.node;
        while (no != null) {
            map.put(no.item.getKey(), no);
            no = no.next;
        }
    }

    public String getNext(String value) {
        String s = Optional.ofNullable(map.get(value))
                .map(Node::getNext)
                .map(item -> item.getItem().getKey())
                .orElse(null);
        if (ObjectUtils.isEmpty(s)) {
            throw new BizException(JsonReturnCode.FLOW_NOT_EXIST_ERROR);
        }
        return s;
    }

    @Data
    private static class Node {
        private FlowEnum item;
        private Node next;

        public Node(FlowEnum item) {
            this.item = item;
        }
    }

}
