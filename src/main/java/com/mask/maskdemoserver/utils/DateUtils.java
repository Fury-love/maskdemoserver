package com.mask.maskdemoserver.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/5/16
 * Time: 16:24
 */
public class DateUtils {

    /**
     * 时间格式
     */
    final public static String TimeFormat_0 = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
        throw new IllegalStateException();
    }

    /**
     * 时间秒相加
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minute).toDate();
    }

    /**
     * 返回值特定格式日期时间型字符串 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static Date getTimeNow() {
        try {
            SimpleDateFormat tempDate = new SimpleDateFormat(TimeFormat_0);
            String nowTime = tempDate.format(new java.util.Date());
            return tempDate.parse(nowTime);
        } catch (Exception e) {
            return null;
        }
    }
}
