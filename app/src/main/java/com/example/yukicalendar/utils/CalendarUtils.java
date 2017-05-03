package com.example.yukicalendar.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtils {

    public static String getDisplayTime(Context context, Calendar cal, Locale locale) {
        String format;
        if (android.text.format.DateFormat.is24HourFormat(context)) {
            format = "HH:mm";
        } else {
            if (cal.get(Calendar.MINUTE) == 0) {
                format = "ha";
            } else {
                format = "h:mma";
            }
        }
        DateFormat df = new SimpleDateFormat(format, locale);
        return df.format(cal.getTime()).toLowerCase();
    }


    public static int getDayOfMonth(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayOfWeek(Calendar calendar) {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    public static String getDisplayTime(Context context, long time, Locale locale) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String format;
        if (android.text.format.DateFormat.is24HourFormat(context)) {
            format = "HH:mm";
        } else {
            if (cal.get(Calendar.MINUTE) == 0) {
                format = "ha";
            } else {
                format = "h:mma";
            }
        }
        DateFormat df = new SimpleDateFormat(format, locale);
        return df.format(cal.getTime()).toLowerCase();
    }

    public static String getDisplayMonth(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        Calendar currentCal = Calendar.getInstance();

        if (currentCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        }
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + cal.get(Calendar.YEAR);
    }

    public static boolean isSameDate(long firstItem, long secondItem) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(firstItem);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(secondItem);

        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
