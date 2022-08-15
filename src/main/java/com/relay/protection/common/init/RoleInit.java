package com.relay.protection.common.init;

import com.relay.protection.mapper.SysMenuMapper;
import com.relay.protection.pojo.dto.UserAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/1 13:56
 */
@Slf4j
@Component
public class RoleInit {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    public Map<Long, List<UserAuth>> userAuthMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("init -----------  role");
        List<UserAuth> list = sysMenuMapper.getUserMenuAllList();
        userAuthMap = list.stream().collect(Collectors.groupingBy(UserAuth::getRoleId));
    }

}
