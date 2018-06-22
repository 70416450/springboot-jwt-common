package com.tzy.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Heaton
 * @date 2018/5/8 11:57
 * @describe 时间工具类
 */
@SuppressWarnings("all")
public class DateUtil {
    private static ThreadLocal<SimpleDateFormat> listThreadLocalM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalY = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalYN = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalYM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMM");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalYMZZ = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalYMD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalY_M = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalY_M_D = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalY_M_D_H_M_S = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> listThreadLocalY_M_D_China = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };

    /**
     * @param [format]
     * @return java.text.SimpleDateFormat
     * @date 2018/5/8 12:04
     * @describe 成功返回{@link SimpleDateFormat  根据需要格式转换需要的时间格式化}
     */
    public static SimpleDateFormat getFormat(String format) {
        SimpleDateFormat sdf;
        if ("MM".equalsIgnoreCase(format)) {
            sdf = listThreadLocalM.get();
        } else if ("yyyyMM".equalsIgnoreCase(format)) {
            sdf = listThreadLocalYM.get();
        } else if ("yyyyMMdd".equalsIgnoreCase(format)) {
            sdf = listThreadLocalYMD.get();
        } else if ("yyyy-MM".equalsIgnoreCase(format)) {
            sdf = listThreadLocalY_M.get();
        } else if ("yyyy-MM-dd".equalsIgnoreCase(format)) {
            sdf = listThreadLocalY_M_D.get();
        } else if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(format)) {
            sdf = listThreadLocalY_M_D_H_M_S.get();
        } else if ("yyyy".equalsIgnoreCase(format)) {
            sdf = listThreadLocalY.get();
        } else if ("yyyy年".equalsIgnoreCase(format)) {
            sdf = listThreadLocalYN.get();
        } else if ("yyyy年MM月dd日".equalsIgnoreCase(format)) {
            sdf = listThreadLocalY_M_D_China.get();
        } else if ("yyyy/MM/dd".equalsIgnoreCase(format)) {
            sdf = listThreadLocalYMZZ.get();
        } else {
            sdf = new SimpleDateFormat(format);
        }
        return sdf;
    }

    /**
     * 判断时间是否在时间段内(以月份区分，如2017-3,2017,-4,2017-5)
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return boolean
     */
    public static boolean betweenTime(Date ifTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(ifTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(DateUtil.upMonth(beginTime));

        Calendar end = Calendar.getInstance();
        end.setTime(DateUtil.downMonth(endTime));

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param [strDate]
     * @return java.util.Date
     * @date 2018/5/31 10:48
     * @describe 成功返回{@link Date},否时间转时间
     */
    public static Date dateToDate(Date currentTime, String format) {
        SimpleDateFormat formatter = getFormat(format);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = getFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * @param [date_str 字符串日期, format 如：yyyy-MM-dd HH:mm:ss]
     * @return java.lang.String
     * @date 2018/5/8 12:07
     * @describe 成功返回{@link String  }日期格式字符串转换成时间戳
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = getFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param []
     * @return java.lang.String
     * @date 2018/5/8 12:06
     * @describe 成功返回{@link String }取得当前时间戳（精确到秒）
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * @param [date]
     * @return java.lang.String
     * @date 2018/5/7 15:02
     * @describe 成功返回{@link String} 时间格式的字符串
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = getFormat(format);
        String str = sdf.format(date);
        return str;
    }

    /**
     * @param [time1, time2, format]
     * @return java.lang.Boolean
     * @date 2018/5/7 19:14
     * @describe 成功返回{@link boolean} 比较时间
     */
    public static Boolean equalsTime(Date time1, Date time2, String format) {
        SimpleDateFormat sdf = getFormat(format);
        String l1 = sdf.format(time1);
        String l2 = sdf.format(time2);
        return l1.equalsIgnoreCase(l2);
    }

    /**
     * @param [time1, time2, format]
     * @return java.lang.Boolean
     * @date 2018/5/7 19:14
     * @describe 成功返回{@link Long} 比较时间
     */
    public static Integer equalsTimeLong(Date time1, Date time2, String format) {
        SimpleDateFormat sdf = getFormat(format);
        Integer i1 = Integer.parseInt(sdf.format(time1));
        Integer i2 = Integer.parseInt(sdf.format(time2));
        return i1 - i2;
    }

    /**
     * @param time1
     * @param time2
     * @param format
     * @return 成功返回{@link Long} 时间数值相加
     */
    public static Integer parseTimeIntegerSum(Integer time1, Date time2, String format) {
        SimpleDateFormat sdf = getFormat(format);
        Integer i2 = Integer.parseInt(sdf.format(time2));
        return time1 + i2;
    }

    /**
     * @param [endTime]
     * @return java.util.Date
     * @date 2018/5/8 12:12
     * @describe 成功返回{@link Date}返回上月的时间
     */
    public static Date upMonth(Date endTime) {
        return upMonth(endTime, -1);
    }

    public static Date upMonth(Date endTime, Integer i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime); // 设置为当前时间
        calendar.add(Calendar.MONTH, i); // 设置为上一年
        return calendar.getTime();
    }

    /**
     * @param [endTime]
     * @return java.util.Date
     * @date 2018/5/8 12:12
     * @describe 成功返回{@link Date}返回下月的时间
     */
    public static Date downMonth(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime); // 设置为当前时间
        calendar.add(Calendar.MONTH, 1); // 设置为下一个月
        return calendar.getTime();
    }

    /**
     * @param [endTime]
     * @return java.util.Date
     * @date 2018/5/8 12:12
     * @describe 成功返回{@link Date}返回上一年时间
     */
    public static Date upYear(Date endTime) {
        return upYear(endTime, -1);
    }

    public static Date upYear(Date endTime, Integer i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime); // 设置为当前时间
        calendar.add(Calendar.YEAR, i); // 设置为上一年
        return calendar.getTime();
    }

    /**
     * @param [time1, format]
     * @return java.lang.Integer
     * @date 2018/5/18 15:02
     * @describe 转换日期为数字
     */
    public static Integer parseTimeInteger(Date time1, String format) {
        SimpleDateFormat sdf = getFormat(format);
        Integer i2 = Integer.parseInt(sdf.format(time1));
        return i2;
    }

    /**
     * @param strDate
     * @return Date
     * String转Date
     */
    public static Date stringToDate(String strDate, String format) {
        SimpleDateFormat formatter = getFormat(format);
        Date strToDate = null;
        try {
            strToDate = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strToDate;
    }

    public static Boolean MonmolOrBimonthly(Date date) {
        String s = date2String(date, "MM");
        switch (s) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "09":
            case "11":
                return false;
            case "02":
            case "04":
            case "06":
            case "08":
            case "10":
            case "12":
                return true;
        }
        return null;
    }


    public static DateInfo getDateInfo(Date time) {
        String yearMonth = DateUtil.date2String(time, "yyyy-MM-dd HH:mm:ss");
        String year = yearMonth.substring(0, 4) + "年";
        String month = yearMonth.substring(5, 7) + "月";
        String yearMonthChina = year + month;
        String day = yearMonth.substring(8, 10) + "日";
        String h = yearMonth.substring(11, 13) + "时";
        String m = yearMonth.substring(14, 16) + "分";
        String s = yearMonth.substring(17, 19) + "秒";
        return new DateInfo(yearMonth, year, month, day, h, m, s, yearMonthChina);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateInfo {
        String yearMonth = null;
        String year = null;
        String month = null;
        String day = null;
        String h = null;
        String m = null;
        String s = null;
        String yearMonthChina = null;
    }

    public static void main(String[] args) {
        String timeStamp = timeStamp();
        System.out.println("timeStamp=" + timeStamp); //运行输出:timeStamp=1470278082
        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);//运行输出:date=2016-08-04 10:34:42

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(timeStamp2);  //运行输出:1470278082

        System.out.println(date2String(downMonth(new Date()), "yyyy-MM"));//下月

        System.out.println(DateUtil.date2String(dateToDate(new Date(), "yyyy-MM"), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(betweenTime(DateUtil.dateToDate(new Date(), "yyyyMM"), DateUtil.dateToDate(new Date(), "yyyyMM"), DateUtil.dateToDate(new Date(), "yyyyMM")));
        System.out.println(DateUtil.date2String(DateUtil.upYear(new Date()), "yyyy-MM"));
        System.out.println(DateUtil.date2String(new Date(), "MM"));
    }


}