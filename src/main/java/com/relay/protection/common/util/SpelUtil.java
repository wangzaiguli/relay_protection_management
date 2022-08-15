package com.relay.protection.common.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/14 15:54
 */
public class SpelUtil {

    public static final String SPEL_MAP_NAME = "map";

    public static boolean contains(String value, String code) {
        return value.contains("#" + SPEL_MAP_NAME + "[" + code + "]");
    }

    public static <T> void executeMap(Map<String, T> map, String expression) {
        EvaluationContext context3 = new StandardEvaluationContext();
        context3.setVariable(SPEL_MAP_NAME, map);
        new SpelExpressionParser().parseExpression(expression).getValue(context3);
    }

//    public static <T> void executeMap2(Map<String, T> map, String expression) {
//        EvaluationContext context3 = new StandardEvaluationContext();
//        context3.setVariable(SPEL_MAP_NAME, map);
//        new SpelExpressionParser().parseExpression(expression).getValue(map, double.class);
//    }

    public static <T> void executeModel(T t, String expression) {
        new SpelExpressionParser().parseExpression(expression).getValue(t);
    }


}
