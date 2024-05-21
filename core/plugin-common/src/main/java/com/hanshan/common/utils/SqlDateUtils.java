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
 */
public class SqlDateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

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
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(dateTime);
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
