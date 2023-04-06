package com.qiyuan.bautil.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTool {
    private static final Logger logger = LoggerFactory.getLogger(TimeTool.class);
    /**
     * 日期格式化串 yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String stringOfDateTimeyyyyMMddHHmmss(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }
    public static String stringOfDateTimeyyyyMMddHH(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyyMMddHH");
        return formatter.format(date);
    }
    public static String stringOfDateTimeStandardFormat(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    public static String stringOfDate(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    public static String stringOfCnDate(java.util.Date date) {
        Format formatter = new SimpleDateFormat("yyyy年M月d日");
        return formatter.format(date);
    }
    public static String stringOfDateTimeStandardFormatNew(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
        return formatter.format(date);
    }
    /**
     * 日期格式化串 yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String stringOfDateTimeyyyyMMdd(java.util.Date date) throws Exception{
        Format formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }
    public static String stringOfDateTime(java.util.Date date,String pattern) throws Exception{
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    public static String stringOfDateTimeyyyyMMdd() throws Exception{
        return TimeTool.stringOfDateTimeyyyyMMdd(new Date());
    }
    public static String stringOfDateTimeYYYY() throws Exception{
        return TimeTool.stringOfDateTime(new Date(),"yyyy");
    }
    public static String stringOfDateTimeYYYY(Date date) throws Exception{
        return TimeTool.stringOfDateTime(date,"yyyy");
    }
    public static String stringOfDateTimeMM() throws Exception{
        return TimeTool.stringOfDateTime(new Date(),"MM");
    }
    public static String stringOfDateTimeMM(Date date) throws Exception{
        return TimeTool.stringOfDateTime(date,"MM");
    }
    public static String stringOfDateTimeDD() throws Exception{
        return TimeTool.stringOfDateTime(new Date(),"dd");
    }
    public static String stringOfDateTimeDD(Date date) throws Exception{
        return TimeTool.stringOfDateTime(date,"dd");
    }
    /**
     * 将日期字符串转换为日期变量,如果有问题,返回null
     * @param str
     * @return
     */
    public static java.util.Date stringToDateTime(String str) throws Exception {
        if(StringUtils.isBlank(str)){
            return null;
        }
//        try {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (java.util.Date)formatter.parse(str);
//        } catch (Exception e) {
//            return null;
//        }
    }
    public static java.util.Date stringToDateTimeWithTry(String str) {
        if(StringUtils.isBlank(str)){
            return null;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (java.util.Date) formatter.parse(str);
        }catch (Exception ex){
            return new Date();
        }

    }
    public static int getCurrentYear() throws Exception{
        Calendar a=Calendar.getInstance();
        return a.get(Calendar.YEAR);
    }
    public static int getCurrentMonth() throws Exception{
        Calendar a=Calendar.getInstance();
        return a.get(Calendar.MONTH);
    }
    //将日期字符串转换为日期变量,如果有问题,返回当前日期
    public static java.util.Date stringToDate(String str, String formatstr) throws Exception {
        DateFormat formatter = new SimpleDateFormat(formatstr);
        return (java.util.Date) formatter.parse(str);
    }
    public static java.util.Date stringToDateyyyyMMddHHmmss(String str) throws Exception {
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return (java.util.Date) formatter.parse(str);
    }
    /**
     * 返回当前日期串，无间隔
     * @return
     */
    public static String getTodayStr() throws Exception{
        java.util.Date d=new java.util.Date();
        return stringOfDateTimeyyyyMMdd(d);
    }
    /**
     *计算并返回两个日期之间的秒数 2006年1月1日
     */
    public static int subSecond(java.util.Date d1, java.util.Date d2) {

        long mss = d2.getTime() - d1.getTime();
        long ss = mss / 1000;


        return (int)ss;
    }
    /**
     *计算数秒后的日期
     */
    public static java.util.Date addDateBySecond(java.util.Date d, int scount) {
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(d);
        gc.add(Calendar.SECOND, scount);
        return gc.getTime();
    }

    /**
     *计算数日后的日期
     */
    public static java.util.Date addDateByDay(java.util.Date d, int dcount) {
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(d);
        gc.add(Calendar.DATE, dcount);
        return gc.getTime();
    }

    /**
     *计算并返回日期中的日
     */
    public static int dayOfDate(java.util.Date d1) {
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(d1);
        return gc.get(Calendar.DAY_OF_MONTH);
    }
    /**
     *计算并返回日期中的月
     */
    public static int monthOfDate(java.util.Date d1) {
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(d1);
        return gc.get(Calendar.MONTH) + 1;
    }
    /**
     *计算并返回日期中的年
     */
    public static int yearOfDate(java.util.Date d1) {
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(d1);
        return gc.get(Calendar.YEAR);
    }

    /**
     * 判断两个时间是否相近（在指定秒数的误差）
     * @param time1
     * @param time2
     * @param secend
     * @return
     */
    public static boolean timeEqualsIgnoreSpan(Date time1,Date time2,int secend){
        int span = TimeTool.subSecond(time1,time2);
        int absSpan = Math.abs(span);
        if(absSpan > secend){
            return false;
        }
        return true;
    }

    /**
     * 取当前时间串，一般用于记签名
     * @return
     * @throws Exception
     */
    public static String stringOfTimeNow(){
        Format formatter = new SimpleDateFormat("HHmmss");
        return formatter.format(new Date());
    }

    public static int subDate(java.util.Date d1, java.util.Date d2) {
        long mss = d2.getTime() - d1.getTime();
        long ss = mss / 1000;
        long ms = ss / 60;
        long hs = ms / 60;
        long ds = hs / 24;
        return (int)ds;
    }

    /**
     * 输入的字符为yyyy-MM-dd  HH:mm:ss类型 转换为：java.util.Date
     */

    public static java.util.Date isDateTime(String datestr) {
        java.util.Date rdatetime = null;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            rdatetime = formatter.parse(datestr,pos);
            return  rdatetime;
        }
        catch (IllegalArgumentException myException){
            return rdatetime;
        }

    }
}

