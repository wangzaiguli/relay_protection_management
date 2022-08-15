package com.relay.protection.common.util;

import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/25 15:25
 */
public class StrUtil {

    public static List<String> str2List(String str, String separate) {
        if(ObjectUtils.isEmpty(str)) {
            new ArrayList<>();
        }
        String[] split = str.split(separate);
        List<String> list = new ArrayList<>(split.length);
        for (String s : split) {
            list.add(s.trim());
        }
        return list;
    }

}
