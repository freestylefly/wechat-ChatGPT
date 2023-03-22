package cn.canghe.robot.groupsave.common.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bin
 * @date 2020/09/09 14:12:53
 * @description 日期时间工具类
 * @menu
 */
public class DateUtils {


    /**
     * 两个日期相隔的小时数
     */
    public static int getDatePoor(Date endDate, Date startDate) {

        long nm = 1000 * 60 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少小时
        long min = diff / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return (int) min;
    }

    /**
     * 当前时间后几分钟数据
     */
    public static String getDateAfterDay(String nowDate, int endDay) {
        //当前时间
        Date currDate = DateUtils.strToDate(nowDate);
        //当前日历时间
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.setTime(currDate);
        // endDay分钟后的时间
        beforeTime.add(Calendar.MINUTE, endDay);
        Date beforeD = beforeTime.getTime();
        return DateUtils.formatTime(beforeD);
    }

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean before(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date dateTime1 = timeFormat.parse(time1);
            java.util.Date dateTime2 = timeFormat.parse(time2);

            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean after(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date dateTime1 = timeFormat.parse(time1);
            java.util.Date dateTime2 = timeFormat.parse(time2);
            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差值（单位为秒）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minus(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date datetime1 = timeFormat.parse(time1);
            java.util.Date datetime2 = timeFormat.parse(time2);

            long millisecond = datetime1.getTime() - datetime2.getTime();

            return Integer.valueOf(String.valueOf(millisecond / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取年月日和小时
     *
     * @param datetime 时间（yyyy-MM-dd HH:mm:ss）
     * @return 结果
     */
    public static String getDateHour(String datetime) {
        String date = datetime.split(" ")[0];
        String hourMinuteSecond = datetime.split(" ")[1];
        String hour = hourMinuteSecond.split(":")[0];
        return date + "_" + hour;
    }

    /**
     * 获取明天日期（yyyy-MM-dd）
     *
     * @return 当天日期
     */
    public static String getTomorrowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //取时间
        java.util.Date date = new java.util.Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, 1);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return dateFormat.format(date);
    }


    /**
     * 获取当天日期（yyyy-MM-dd）
     *
     * @return 当天日期
     */
    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new java.util.Date());
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     *
     * @return 昨天的日期
     */
    public static String getYesterdayDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);

        java.util.Date date = cal.getTime();

        return dateFormat.format(date);
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     *
     * @return 昨天的日期
     */
    public static Date getYesterdayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);

        java.util.Date date = cal.getTime();

        return date;
    }

    /**
     * 获取明天日期（yyyy-MM-dd）
     *
     * @return 前五天的日期
     */
    public static String getBeforeFiveDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //取时间
        java.util.Date date = new java.util.Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, -4);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return dateFormat.format(date);
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     *
     * @param date Date对象
     * @return 格式化后的日期
     */
    public static String formatDate(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatTime(java.util.Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeFormat.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatToTime(java.util.Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }

    /**
     * 获取近五天的日期（yyyy-MM-dd）
     *
     * @return 近五天的日期
     */
    public static List<String> getNearlyFiveDate() {
        SimpleDateFormat dateScreenFormat = new SimpleDateFormat("M/d");
        //取时间
        java.util.Date date = new java.util.Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date = calendar.getTime();
        List<String> timeList = new ArrayList<>(5);
        timeList.add(dateScreenFormat.format(date));
        for (int i = 0; i < 4; i++) {
            //把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            timeList.add(dateScreenFormat.format(date));
        }
        // 返回时间集合
        Collections.reverse(timeList);
        return timeList;
    }

    /**
     * @function 获取所属星期
     * @params
     **/
    public static String dayForWeek() {

        Calendar cal = Calendar.getInstance();

        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};

        try {

            cal.setTime(new Date());

        } catch (Exception e) {

            e.printStackTrace();

        }

        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0) {

            w = 0;
        }
        return weekDays[w];
    }

    /**
     * @function 获取所属星期-数字
     * @params
     **/
    public static String dayForWeekNum() {

        Calendar cal = Calendar.getInstance();

        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};

        try {

            cal.setTime(new Date());

        } catch (Exception e) {

            e.printStackTrace();

        }

        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0) {

            w = 0;
        }
        return weekDays[w];
    }


    /**
     * @function string->date
     * @params time
     **/
    public static Date strToDate(String time) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return timeFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}