package com.relay.protection.common.util;

import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 工具类
 */
@Slf4j
@UtilityClass
public class DateUtil {
    //格式化日期

    // 列"2018"  length = 4
    public static final String DATE_Y = "yyyy";
    private static final String FORMAT_Y = "[0-9]{4}";

    // 列"201806"  length = 6
    public static final String DATE_YM = "yyyyMM";
    private static final String FORMAT_YM = "[0-9]{6}";

    // 列"2018-06"  length = 7
    public static final String DATE_YM_ = "yyyy-MM";
    private static final String FORMAT_YM_ = "[0-9]{4}-[0-9]{2}";

    // 列"20180602"  length = 8
    public static final String DATE_YMD = "yyyyMMdd";
    private static final String FORMAT_YMD = "[0-9]{8}";

    // 列"20180602212023"  length = 14
    public static final String DATE_YMDHMS = "yyyyMMddHHmmss";
    private static final String FORMAT_YMDHMS = "[0-9]{14}";

    // 列"2018-06-02"  length = 10
    public static final String DATE_YMD_ = "yyyy-MM-dd";
    private static final String FORMAT_YMD_ = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    // 列"2018-06-02 21:03:23"  length = 19
    public static final String DATE_YMDHMS_ = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_YMDHMS_ = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

    // 列"2018-06-02 21:03:23.1"  length = 21
    public static final String DATE_YMDHMSS1_ = "yyyy-MM-dd HH:mm:ss.s";
    private static final String FORMAT_YMDHMSS1_ = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{1}";

    // 列"2018-06-02 21:03:23.123"  length = 23
    public static final String DATE_YMDHMSS3_ = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String FORMAT_YMDHMSS3_ = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}";

    // 列"2018-06-02T21:03:23"  length = 19
    public static final String DATE_YMDHMS_T = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String FORMAT_YMDHMS_T = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}";

    // 格林威治时间转回Date类型: 加时区
    public static final String DATE_CST = "EEE MMM dd HH:mm:ss z yyyy";

    // 带有空格，（单机窗口下载的数据中的日期的格式）
    public static final String DATE_YMDHMS_N = "yyyy - MM - dd hh: mm: ss";


    /**
     * 获取SimpleDateFormat（格式默认yyyy-MM-dd）
     *
     * @return SimpleDateFormat
     */
    public static DateTimeFormatter getDtfdt() {
        return getDtf(DATE_YMDHMS_);
    }

    /**
     * 获取SimpleDateFormat（格式默认yyyy-MM-dd）
     *
     * @return SimpleDateFormat
     */
    public static DateTimeFormatter getDtfd() {
        return getDtf(DATE_YMD_);
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param dateFormat 需要格式化的格式
     * @return SimpleDateFormat
     */
    public static DateTimeFormatter getDtf(String dateFormat) {

        return DateTimeFormatter.ofPattern(dateFormat);

    }

    /**
     * 日期-> 字符串（格式默认yyyy-MM-dd）
     *
     * @param date 日期
     * @return 字符串
     */
    public static String date2Str(LocalDateTime date) {
        return date2Str(date, DATE_YMD_);
    }

    /**
     * 日期-> 字符串
     *
     * @param date       日期
     * @param dateFormat 格式
     * @return 字符串
     */
    public static String date2Str(LocalDateTime date, String dateFormat) {
        String str = null;
        if (!ObjectUtils.isEmpty(date)) {
            DateTimeFormatter sdf = getDtf(dateFormat);
            str = sdf.format(date);
        }
        return str;
    }

    /**
     * 字符串-> 日期
     *
     * @param fmt     SimpleDateFormat
     * @param strDate 字符串
     * @return 日期
     */
    public static LocalDateTime str2Date(DateTimeFormatter fmt, String strDate) {
        try {
            return LocalDateTime.parse(strDate, fmt);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new BizException(JsonReturnCode.DATE_FORMTER_ERROR);

        }
    }

    /**
     * 字符串-> 日期
     *
     * @param strDate 字符串
     * @return 日期
     */
    public static LocalDateTime str2Date(String strDate) {
        try {
            DateTimeFormatter fmt = getDtfdt();
            if (strDate.length() < 11) {
                strDate += " 00:00:00";
            }
            return LocalDateTime.parse(strDate, fmt);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new BizException(JsonReturnCode.DATE_FORMTER_ERROR);

        }
    }


    /**
     * 将 Date 转为 LocalDateTime
     *
     * @param date
     * @return java.time.LocalDateTime;
     */
    public static LocalDateTime date2DateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static void main(String[] args) {
        DateUtil test = new DateUtil();
        LocalDateTime localDateTime = str2Date("2021-12-12");
        String s = date2Str(LocalDateTime.now());

        System.out.println(s);
    }

}
