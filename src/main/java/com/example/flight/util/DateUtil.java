package com.example.flight.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //在原来基础上增加一个月
    public static Date subMonth(Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, 1);
        return rightNow.getTime();
    }

    // 在原来日期上增减一天(减为-1)
    public static Date subDay(Date date,int day){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_MONTH, day);
        System.out.println(rightNow.getTime());
        return rightNow.getTime();
    }
}
