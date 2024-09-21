package com.hanshan.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Time;

/**
 * 时间工具类
 *
 * @author ruoyi
 * yyyy-MM-dd：表示年月日，如 2023-10-23
 * yyyy/MM/dd：使用斜杠分隔年月日，如 2023/10/23
 * yyyy.MM.dd：使用点分隔年月日，如 2023.10.23
 * dd/MM/yyyy：日期在前，年月在后，如 23/10/2023
 * HH:mm:ss：表示24小时制的小时、分钟和秒，如 14:30:15
 * hh:mm:ss a：表示12小时制的小时、分钟和秒，并附带上午（AM）或下午（PM）标记，如 02:30:15 PM
 * yyyy-MM-dd HH:mm:ss：同时包含年月日和时分秒，如 2023-10-23 14:30:15
 * yyyy-MM-dd'T'HH:mm:ss.SSSXXX：ISO 8601格式的日期时间，包括毫秒和时区信息，如 2023-10-23T14:30:15.123+08:00
 * EEE, MMM d, yyyy HH:mm:ss a：英文的星期、月份缩写和日期时间，如 Sun, Oct 23, 2023 02:30:15 PM
 * EEEE, MMMM dd, yyyy：英文的星期、月份全称和日期，如 Sunday, October 23, 2023
 * 请注意，Java中的日期和时间格式模式使用字母来表示不同的日期和时间组件，其中：
 *
 * y 代表年份
 * M 代表月份
 * d 代表日期（一个月中的第几天）
 * H 代表小时（24小时制）
 * h 代表小时（12小时制）
 * m 代表分钟
 * s 代表秒
 * a 代表上午或下午（仅12小时制）
 * E 代表星期几
 * S 代表毫秒（如果指定了多个S，可以表示不同数量的毫秒，如SSS表示三位毫秒）
 * X 代表时区（如XXX表示ISO 8601时区）
 */
public class SqlDateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String HH_mm_ss = "HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate(Date date)
    {
        return parseDateToStr(YYYY_MM_DD,date);
    }

    public static String getYear(Date date)
    {
        return parseDateToStr(YYYY,date);
    }
    public static String getTimeStamp(Timestamp dateTime)
    {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS).format(dateTime);
    }
//
    public static final String getTime(Time time)
    {
        return new SimpleDateFormat(HH_mm_ss).format(time);
    }
//
//    public static final String getTimeStamp()
//    {
//        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
//    }
//
//
//    public static final String dateTimeNow(final String format)
//    {
//        return parseDateToStr(format, new Date());
//    }
//
//    public static final String dateTime(final Date date)
//    {
//        return parseDateToStr(YYYY_MM_DD, date);
//    }
//
    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }
//
//    public static final Date dateTime(final String format, final String ts)
//    {
//        try
//        {
//            return new SimpleDateFormat(format).parse(ts);
//        }
//        catch (ParseException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//

}
