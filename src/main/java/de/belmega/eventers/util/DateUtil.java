package de.belmega.eventers.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * Creates a date object with the day of the first parameter and the time of the second parameter.
     * @param date
     * @param time
     * @return
     */
    public static Date combineDateTime(Date date, Date time) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);
        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

        return dateCal.getTime();
    }
}
