package com.jm.core.common.tools.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用于处理日期工具类
 *
 * @author xiejinxiong
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 获取当前年份
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH)
                + 1;// +1是因为返回来的值并不是代表月份，而是对应于Calendar.MAY常数的值，
        // Calendar在月份上的常数值从Calendar.JANUARY开始是0，到Calendar.DECEMBER的11
    }

    /**
     * 获取当前的时间为该月的第几天
     */
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前的时间为该周的第几天
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前时间为该天的多少点
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        // Calendar calendar = Calendar.getInstance();
        // System.out.println(calendar.get(Calendar.HOUR_OF_DAY)); // 24小时制
        // System.out.println(calendar.get(Calendar.HOUR)); // 12小时制
    }

    /**
     * 获取当前的分钟时间
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 通过获得年份和月份确定该月的日期分布
     */
    public static int[][] getMonthNumFromDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);// -1是因为赋的值并不是代表月份，而是对应于Calendar.MAY常数的值，

        int days[][] = new int[6][7];// 存储该月的日期分布

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得该月的第一天位于周几（需要注意的是，一周的第一天为周日，值为1）

        int monthDaysNum = getMonthDaysNum(year, month);// 获得该月的天数
        // 获得上个月的天数
        int lastMonthDaysNum = getLastMonthDaysNum(year, month);

        // 填充本月的日期
        int dayNum = 1;
        int lastDayNum = 1;
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (i == 0 && j < firstDayOfWeek - 1) {// 填充上个月的剩余部分
                    days[i][j] = lastMonthDaysNum - firstDayOfWeek + 2 + j;
                } else if (dayNum <= monthDaysNum) {// 填充本月
                    days[i][j] = dayNum++;
                } else {// 填充下个月的未来部分
                    days[i][j] = lastDayNum++;
                }
            }
        }

        return days;

    }

    /**
     * 根据年数以及月份数获得上个月的天数
     */
    public static int getLastMonthDaysNum(int year, int month) {

        int lastMonthDaysNum = 0;

        if (month == 1) {
            lastMonthDaysNum = getMonthDaysNum(year - 1, 12);
        } else {
            lastMonthDaysNum = getMonthDaysNum(year, month - 1);
        }
        return lastMonthDaysNum;

    }

    /**
     * 根据年数以及月份数获得该月的天数
     *
     * @return 若返回为负一，这说明输入的年数和月数不符合规格
     */
    public static int getMonthDaysNum(int year, int month) {

        if (year < 0 || month <= 0 || month > 12) {// 对于年份与月份进行简单判断
            return -1;
        }

        int[] array = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};// 一年中，每个月份的天数

        if (month != 2) {
            return array[month - 1];
        } else {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年判断
                return 29;
            } else {
                return 28;
            }
        }

    }

    /**
     * 获取日期的(格式：2017-05)
     */
    public static String getStrDataMonth(Calendar calendar) {
        StringBuilder builder = new StringBuilder();
        builder.append(calendar.get(Calendar.YEAR));
        builder.append('-');
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month <= 9) {
            builder.append(0);
        }
        builder.append(month);
        return builder.toString();
    }


    /**
     * 获取日期的(格式：2017-05-25)
     */
    public static String getStrDataDay(Calendar calendar) {
        StringBuilder builder = new StringBuilder();
        builder.append(getStrDataMonth(calendar));
        builder.append('-');
        int date = calendar.get(Calendar.DATE);
        if (date <= 9) {
            builder.append(0);
        }
        builder.append(date);
        return builder.toString();
    }


    /**
     * 获取日期的(格式：2017-05-25 14:25)
     */
    public static String getStrDataMin(Calendar calendar) {
        StringBuilder builder = new StringBuilder();
        builder.append(getStrDataDay(calendar));

        builder.append(' ');

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <= 9) {
            builder.append(0);
        }
        builder.append(hour);

        builder.append(':');

        int min = calendar.get(Calendar.MINUTE);
        if (min <= 9) {
            builder.append(0);
        }
        builder.append(min);

        return builder.toString();
    }

    /**
     * 获取日期的(格式：2017-05-25 14:25:00)
     */
    public static String getStrDataSecond(Calendar calendar) {
        StringBuilder builder = new StringBuilder();
        builder.append(getStrDataMin(calendar));
        builder.append(':');

        int second = calendar.get(Calendar.SECOND);
        if (second <= 9) {
            builder.append(0);
        }
        builder.append(second);

        return builder.toString();
    }

    /**
     * 获取人的年龄
     */
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException("年龄不能超过当前日期");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
            if (nowDayOfYear < bornDayOfYear) {
                age -= 1;
            }
        }
        return age;
    }

    /**
     * 获取人的年龄
     *
     * @param data 日期
     * @param type 对应的格式，如："yyyy-MM-dd"
     */
    public static int getAge(String data, String type) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat(type);
        Date mydate = myFormatter.parse(data);
        return getAge(mydate);
    }

    /**
     * 获取Calander
     *
     * @param data 日期
     * @param type 对应的格式，如："yyyy-MM-dd" "yyyy-MM-dd HH:mm:ss"
     */
    public static Calendar getCalander(String data, String type) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat(type);
        Date mydate = myFormatter.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mydate);
        return calendar;
    }

    /**
     * 由2017-07-21 12:34:26 转为 07月21日 12:34 格式
     */
    public static String formatString1(String data) {
        return data.substring(5, 16).replace('-', '月').replace(" ", "日 ");
    }

    /**
     * 由2017-07-21 12:34:26 转为 今日 12:34 或 昨日 12:34 或 2017.07.21 12:34格式
     */
    public static String formatString2(String data) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarNow = (Calendar) calendar.clone();
        String[] strDate = data.substring(0, data.indexOf(' ')).split("-");
        calendar.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]) - 1,
                Integer.parseInt(strDate[2]));
        if (calendar.compareTo(calendarNow) == 0) {
            return "今日" + data.substring(10, data.length() - 3);
        } else {
            calendarNow.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            if (calendar.compareTo(calendarNow) == 0) {
                return "昨日" + data.substring(10, data.length() - 3);
            } else {
                return data.replace('-', '.')
                        .substring(0, data.length() - 3);
            }
        }
    }

    private static String getAstro(int month, int day) {
        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座",
                "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
        int[] DayArr = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  // 两个星座分割日
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < DayArr[month - 1]) {
            index = index - 1;
        }
        // 返回索引指向的星座string
        return starArr[index];
    }

    /**
     * 获取开始年月到结束年月的时间列表
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> getStartEndDate(Calendar start, Calendar end) {
        List<String> dates = new ArrayList<>();
        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH);

        int startYear;
        int startMonth;

        while (true) {
            startYear = start.get(Calendar.YEAR);
            startMonth = start.get(Calendar.MONTH);

            if (startYear > endYear || (startYear == endYear && startMonth > endMonth)) {
                break;
            }

            startMonth++;

            dates.add(startYear + "-" + (startMonth > 9 ? "" : "0") + startMonth);

            start.add(Calendar.MONTH, 1);

        }
        return dates;
    }

    /**
     * 获取两个时间间隔
     *
     * @param timeStart
     * @param timeEnd
     * @return
     * @throws ParseException
     */
    public static String requestTimeBetween(String timeStart, String timeEnd) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date begin = dfs.parse(timeStart);
        java.util.Date end = dfs.parse(timeEnd);
        //除以1000是为了转换成秒
        long between = (end.getTime() - begin.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;

//        return "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
        StringBuilder stringBuilder = new StringBuilder();
        if (day1 > 0) {
            stringBuilder.append(day1 + "天");
        }
        if (hour1 > 0) {
            stringBuilder.append(hour1 + "小时");
        }
        if (minute1 > 0) {
            stringBuilder.append(minute1 + "分");
        }
        if (second1 > 0) {
            stringBuilder.append(second1 + "秒");
        } else {
            if (stringBuilder.toString().length() == 0) {
                stringBuilder.append(second1 + "秒");
            }
        }

        return stringBuilder.toString();
    }


    /***
     * yyyy-MM-dd HH:mm:ss
     */
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    /***
     * 两个日期相差多少秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getTimeDelta(Date date1, Date date2) {
        //单位是秒
        long timeDelta = (date1.getTime() - date2.getTime()) / 1000;
        int secondsDelta = timeDelta > 0 ? (int) timeDelta : (int) Math.abs(timeDelta);
        return secondsDelta;
    }

    /***
     * 两个日期相差多少秒
     * @param dateStr1  :yyyy-MM-dd HH:mm:ss
     * @param dateStr2 :yyyy-MM-dd HH:mm:ss

     */
    public static int getTimeDelta(String dateStr1, String dateStr2) {
        Date date1 = parseDateByPattern(dateStr1, yyyyMMddHHmmss);
        Date date2 = parseDateByPattern(dateStr2, yyyyMMddHHmmss);
        return getTimeDelta(date1, date2);
    }

    public static Date parseDateByPattern(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
