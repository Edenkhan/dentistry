package com.youruan.dentistry.core.base.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取传⼊时间的最后⼀秒时间
     */
    public static Date getLastTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    /**
     * 获取传⼊时间的开始时间
     */
    public static Date getStartTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    /**
     * 增加天数，给定⼀个时间增加传⼊的天数
     */
    public static Date addTime(Date date,int dayNum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,dayNum);

        return calendar.getTime();
    }

    /**
     * 减少天数，给定⼀个时间减少传⼊的天数
     */
    public static Date decTime(Date date,int dayNum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-dayNum);

        return calendar.getTime();
    }

    /**
     * 得到两个时间的间隔（天）
     */
    public static long interval(Date date1,Date date2){
        long mill = Math.abs(date1.getTime() - date2.getTime());
        return mill / 1000 / 60 / 60 / 24;
    }

    public static void main(String[] args) {
        Date date = getStartTime(new Date());
        System.out.println(date);
    }

}
