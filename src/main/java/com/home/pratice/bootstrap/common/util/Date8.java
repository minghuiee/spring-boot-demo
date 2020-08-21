package com.home.pratice.bootstrap.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.management.timer.Timer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//TimeUnit轉換時間單位
//org.joda.time.* 時間相關
//javax.management.timer.Timer.* 取得時間單位

@Slf4j
public class Date8 {
    public static String Date4y2M2d = "yyyyMMdd";
    public static String DateWithSlash4y2M2d = "yyyy/MM/dd";
    public static String DateWithDash4y2M2d = "yyyy-MM-dd";
    public static String Time2H2m2s3S = "HHmmssSSS";
    public static String Time2H2m2s = "HHmmss";
    public static String TimeWithColon2H2m2s3S = "HH:mm:ss.SSS";
    public static String TimeWithColon2H2m2s = "HH:mm:ss";
    public static String DateTimeWithDecimal = "yyyyMMddHHmmssSSS";
    public static String DateTime = "yyyyMMddHHmmss";
    public static String DateTimeWithSlashDecimal = "yyyy/MM/dd HH:mm:ss.SSS";
    public static String DateTimeWithSlash = "yyyy/MM/dd HH:mm:ss";
    public static String DateTimeWithDashDecimal = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String DateTimeWithDash = "yyyy-MM-dd HH:mm:ss";
    public static String DateTimeWithDashWithT = "yyyy-MM-ddTHH:mm:ss";
    public static long ONE_SECOND = Timer.ONE_SECOND;
    public static long ONE_MINUTE = Timer.ONE_MINUTE;
    public static long ONE_HOUR = Timer.ONE_HOUR;
    public static long ONE_DAY = Timer.ONE_DAY;
    public static long ONE_WEEK = Timer.ONE_WEEK;
    public static TimeUnit NANOSECONDS = TimeUnit.NANOSECONDS;
    public static TimeUnit MICROSECONDS = TimeUnit.MICROSECONDS;
    public static TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;
    public static TimeUnit SECONDS = TimeUnit.SECONDS;
    public static TimeUnit MINUTES = TimeUnit.MINUTES;
    public static TimeUnit HOURS = TimeUnit.HOURS;

    /**
     * System > Clock > Date > Instant > Calendar
     * System.currentTimeMillis();
     * Clock.systemUTC().millis();
     * new Date().getTime();
     * Instant.now().toEpochMilli()
     * Calendar.getInstance().getTimeInMillis();
     *
     * @return
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    public static String now(DateTimeFormatter format) {
        return LocalDateTime.now().format(format);
    }

    public static DateTimeFormatter Pattern(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    public static long milliToSecond(long millis) {
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }

//    /**
//     * string time to Epoch second(效率低)
//     *
//     * @param time yyyy-MM-ddThh:mm:ss
//     */
//    public static long timeToEpochSecond(String time) {
//        return milliToSecond(timeToEpochMilliseconds(time));
//    }
//
//    /**
//     * string time to Epoch millisecond(效率低)
//     *
//     * @param time yyyy-MM-ddThh:mm:ss
//     */
//    public static long timeToEpochMilliseconds(String time) {
//        LocalDateTime localDateTime = LocalDateTime.parse(time);
//        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//    }

    /**
     * string time to Epoch second
     *
     * @param time yyyy-MM-ddThh:mm:ss
     */
    public static long timeToSecond(String time) {
        return milliToSecond(timeToMilliseconds(time));
    }

    /**
     * string time to Epoch millisecond
     *
     * @param time yyyy-MM-ddThh:mm:ss
     */
    public static long timeToMilliseconds(String time) {
        return dateTimeToMilliseconds(LocalDateTime.parse(time));
    }

    public static long timeToMilliseconds(String time, String format) {
        return dateTimeToMilliseconds(parseDateTime(time, format));
    }

    //==============================================================
    //parse time
    public static LocalDateTime parseDateTime(String time, String format) {
        return LocalDateTime.parse(time, Pattern(format));
    }

    public static Date parseDate(String time, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(time);
    }

    //==============================================================
    //LocalDateTime convert ?
    public static String dateTimeToString(LocalDateTime dateTime, String format) {
        return dateTime.format(Pattern(format));
    }

    public static Timestamp dateTimeToTimestamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    public static long dateTimeToMilliseconds(LocalDateTime dateTime) {
        return dateTimeToTimestamp(dateTime).getTime();
    }

    public static Instant dateTimeToString(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static Date dateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTimeToString(dateTime));
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date localTimeToDate(LocalTime localTime, int year, Month month, int dayOfMonth) {
        return Date.from(localTime.atDate(LocalDate.of(year, month, dayOfMonth)).atZone(ZoneId.systemDefault()).toInstant());
    }

    //==============================================================
    //Timestamp convert ?
    public static LocalDateTime timestampToDateTime(long timestamp) {
        return LocalDateTime.ofInstant(new Timestamp(timestamp).toInstant(), ZoneId.systemDefault());
    }

    public static Date timestampToDate(long timestamp) {
        return InstantToDate(new Timestamp(timestamp).toInstant());
    }

    //==============================================================
    //long convert ?
    public static LocalDateTime longToDateTime(long timestamp) {
        return InstantToDateTime(Instant.ofEpochMilli(timestamp));
    }

    public static Date longToDate(long timestamp) {
        return new Date(timestamp);
    }

    //==============================================================
    //Instant convert ?
    public static LocalDateTime InstantToDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date InstantToDate(Instant instant) {
        return Date.from(instant);
    }

    //==============================================================
    //Date convert ?
    public static String DateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Long DateToLong(Date date) {
        return date.getTime();
    }

    public static Timestamp DateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Instant DateToInstant(Date date) {
        return date.toInstant();
    }

    public static LocalDateTime DateToDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
