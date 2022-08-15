package com.relay.protection.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/6/11 9:41
 */
@UtilityClass
@Slf4j
public class SentUtil {

    public static final String TEST_TITLE = "继电保护管理系统测试通知";
    public static final String TEST_CONTENT = "%s，您好，欢迎使用继电保护管理系统。";

    public static String sent(SentModel sentModel) {
        JSONObject json = new JSONObject();
        if (!ObjectUtils.isEmpty(sentModel.getTemplate())) {
            json.put(Constant.TITLE, sentModel.getTemplate());
        } else {
            json.put(Constant.TEMPLATE, Constant.TEMPLATE_TYPE);
        }
        if (!ObjectUtils.isEmpty(sentModel.getTitle())) {
            json.put(Constant.TITLE, sentModel.getTitle());
        }
        json.put(Constant.CONTENT, sentModel.getContent());
        json.put(Constant.TOKEN, sentModel.getToken());
        String values = json.toString();
        log.info(values);
        String s = HttpUtil.doPost(Constant.PUSHPLUS_PATH, values);
        log.info(s);
        return getMsg(s);
    }

    public static String sent(String token, String title, String content) {
        JSONObject json = new JSONObject();
        json.put(Constant.TEMPLATE, Constant.TEMPLATE_TYPE);
        json.put(Constant.TITLE, title);
        json.put(Constant.CONTENT, content);
        json.put(Constant.TOKEN, token);
        return getMsg(HttpUtil.doPost(Constant.PUSHPLUS_PATH, json.toString()));
    }


    private static String getMsg(String str) {
        try {
            ResultModel resultModel = JSON.parseObject(str, ResultModel.class);
            return resultModel.getMsg();
        } catch (Exception e) {
            return "请求失败。";
        }
    }

    @Data
    public static class SentModel {

        private String token;

        private String title;

        private String content;

        private String template;

    }

    @Data
    public static class ResultModel {

        private String code;

        private String msg;

        private String data;

        private String count;

    }

    @Data
    public static class CourseModel implements Cloneable {

        @JSONField(name = "token", serialize = false)
        private String token;

        @JSONField(name = "课程名称")
        private String name;

        @JSONField(name = "科目")
        private String courseName;

        @JSONField(name = "教室")
        private String room;

        @JSONField(name = "上课时间")
        private String startTime;

        @JSONField(name = "下课时间")
        private String endTime;

        public CourseModel() {
        }

        public CourseModel(String token, String name, String courseName, String room, String startTime, String endTime) {
            this.token = token;
            this.name = name;
            this.courseName = courseName;
            this.room = room;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public CourseModel clone() {
            return new CourseModel(this.token, this.name, this.courseName, this.room, this.startTime, this.endTime);
        }

        public CourseModel token(String token) {
            this.token = token;
            return this;
        }

    }

}
