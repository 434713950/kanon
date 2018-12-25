package com.github.kanon.generate.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: peng_cheng
 * @Description: 日期工具包
 */
public class DateUtil {

    /**
     * 北京时区
     */
    public static final String TIME_ZONE = "GMT+8";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * HH:mm:ss
     */
    public static final String DATE_DAY_TIME_FORMAT = "HH:mm:ss";

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_TIME_DETAIL = "yyyyMMddHHmmss";

    /**
     * yyyy-MM-dd XX
     */
    public static final String DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd XX";

    /**
     * 统一时间格式转换器
     * @param date   时间
     * @param format 时间格式format
     * @return String
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间
     * @return String
     */
    public static String dateTimeFormat(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date 时间
     * @return String
     */
    public static String dateFormat(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 时间解析器
     *
     * @param dateStr 时间字符串
     * @param format  转换格式
     * @return Date
     * @throws ParseException 解析错误
     */
    public static Date parser(String dateStr, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dateStr);
    }

    /**
     * yyyy-MM-dd HH:mm:ss时间解析器
     *
     * @param dateStr 时间字符串
     * @return Date
     * @throws ParseException 解析错误
     */
    public static Date dateTimeParser(String dateStr) throws ParseException {
        return parser(dateStr, DATE_TIME_FORMAT);
    }

    /**
     * yyyy-MM-dd时间解析器
     *
     * @param dateStr 时间字符串
     * @return Date
     * @throws ParseException 解析错误
     */
    public static Date dateParser(String dateStr) throws ParseException {
        return parser(dateStr, DATE_FORMAT);
    }

    /**
     * 得到系统时间的前一天
     * @param date 日期
     * @return Date
     */
    public static Date getBeforeDay(Date date) {
        return getWarpDay(date,-1);
    }

    /**
     * 获取系统时间下一天
     * @param date  日期
     * @return
     */
    public static Date getNextDay(Date date){
        return getWarpDay(date,1);
    }

    /**
     * 获取当前时间的前后几天
     * @param date 日期
     * @param day  天数（+：后几天，-：前几天）
     * @return Date
     */
    public static Date getWarpDay(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }


    /**
     * 获取某天的开始时间
     * @param date  日期
     * @return
     */
    public static Date getDayStart(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置时为0点
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //设置分钟为0分
        calendar.set(Calendar.MINUTE, 0);
        //设置秒为0秒
        calendar.set(Calendar.SECOND, 0);
        //设置毫秒为000
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间
     * @param date  日期
     * @return
     */
    public static Date getDayEnd(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置时为23点
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //设置分钟为59分
        calendar.set(Calendar.MINUTE, 59);
        //设置秒为59秒
        calendar.set(Calendar.SECOND, 59);
        //设置毫秒为999
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当前月份的第一天
     * @param date 日期
     * @return
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        return calendar.getTime();
    }

    /**
     * 获取当前月份的最后一天
     * @param date 日期
     * @return
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取某月最小天数
        int firstDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        return calendar.getTime();
    }


    /**
     * 获取前后某个月的第一天，0为当前日期所在的月份
     * @param date   日期
     * @param amount 间隔月份
     * @return
     */
    public static Date getFirstDayOfPreMonth(Date date, Integer amount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return firstDayOfMonth(calendar.getTime());
    }

    /**
     * 获取前后某个月的最后一天，0为当月
     * @param date   时间
     * @param amount 间隔月数
     * @return
     */
    public static Date getLstDayOfPreMonth(Date date, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return lastDayOfMonth(calendar.getTime());
    }

    /**
     * 日期转星期
     * @param date 日期
     * @return 星期几
     */
    public static Integer dateToWeek(Date date){
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayStart(date));
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 日期转星期String
     * @param date 时间
     * @return 星期几
     */
    public static String dateToWeekStr(Date date) throws ParseException {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayStart(date));
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weeks[weekIndex];
    }

    /**
     * 当前日期是当月的第几天
     * @param date 日期
     * @return Integer
     */
    public static Integer monthDay(Date date) {
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 指示一个月中的某天。
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前月份第几周
     * @param date 日期
     * @return Integer
     */
    public static Integer weekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayStart(date));
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取系统当前时间
     * @return
     */
    public static Date getCurrentSystemTime(){
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当天初始时间
     * @return Date
     */
    public static Date getCurrentStartDay() {
        return getDayStart(getCurrentSystemTime());
    }

    /**
     * 获取当天结束时间
     * @return Date
     */
    public static Date getCurrentEndDay() {
        return getDayEnd(getCurrentSystemTime());
    }
}
