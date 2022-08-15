package com.relay.protection.common.init;

import com.relay.protection.common.enums.BaseEnum;
import com.relay.protection.common.enums.DecideEnum;
import com.relay.protection.common.enums.FlowEnum;
import com.relay.protection.common.enums.MenuTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author zhuHx
 * @version 1.0
 * @date 2022/5/27 13:13
 */
@Data
@Slf4j
@Component
public class SelectInit {

    public static final Map<String, List<Node>> SELECT_NODE_MAP = new HashMap<>();
    public static final Map<String, Map<String, String>> SELECT_CONVTER_MAP = new HashMap<>();


    public static final String FLOW = "flow";
    public static final String MENU_TYPE = "menuType";
    public static final String DECIDE = "decide";

    @PostConstruct
    public void init() {
        log.info("init -----------  select");
        add(FLOW, FlowEnum.values());
        add(MENU_TYPE, MenuTypeEnum.values());
        add(DECIDE, DecideEnum.values());

        for (Map.Entry<String, List<Node>> entry : SELECT_NODE_MAP.entrySet()) {
            Map<String, String> m = entry.getValue().stream().collect(Collectors.toMap(Node::getKey, Node::getValue));
            SELECT_CONVTER_MAP.put(entry.getKey(), m);
        }
    }

    private <T extends BaseEnum> void add(String key, T[] me) {
        SELECT_NODE_MAP.put(key, Arrays.stream(me).map(v -> new Node(v.getKey(), v.getValue())).collect(Collectors.toList()));
    }

    @Data
    public static class Node {
        private String key;
        private String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public <T> void convter(String key, List<T> list, Function<T, String> function, BiConsumer<T, String> biConsumer) {
        Map<String, String> m = SELECT_CONVTER_MAP.get(key);
        for (T t : list) {
            biConsumer.accept(t, m.get(function.apply(t)));
        }
    }

    public <T> void convter(String key, T t, Function<T, String> function, BiConsumer<T, String> biConsumer) {
        Map<String, String> m = SELECT_CONVTER_MAP.get(key);
        biConsumer.accept(t, m.get(function.apply(t)));
    }

}
