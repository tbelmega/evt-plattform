package de.belmega.eventers.util;

import de.belmega.eventers.booking.EventTime;

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

    public static Date combineDateTime(Date date, EventTime time) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        dateCal.set(Calendar.HOUR_OF_DAY, time.getHours());
        dateCal.set(Calendar.MINUTE, time.getMinutes());

        return dateCal.getTime();
    }
}
