package com.x3110.learningcommunity.util;

import java.util.Calendar;

public class TimeUtil {
    public String getTime(){
        System.out.println("getTime...util...");
        Calendar calendar=Calendar.getInstance();
        Long date=calendar.getTime().getTime();
        return date.toString();
    }

    public boolean cmpTimes(String time){
        System.out.println("cmpTime...util...");
        long tempTime=Long.parseLong(time);
        System.out.println("tempTime"+tempTime);

        Calendar calendar=Calendar.getInstance();
        Long date=calendar.getTime().getTime();
        System.out.println("date"+date);

        if(date-tempTime>600000){
            return false;
        }
        else  return true;
    }


}
