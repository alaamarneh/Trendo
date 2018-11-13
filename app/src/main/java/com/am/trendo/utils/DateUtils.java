package com.am.trendo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {
    public static String getDay(long time){
        Date date = new Date(time);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        return outFormat.format(date);
    }
    public static String getMonthAndDayName(long time){
        Date date = new Date(time);
        SimpleDateFormat outFormat = new SimpleDateFormat("MMMM dd");
        return outFormat.format(date);
    }
}
