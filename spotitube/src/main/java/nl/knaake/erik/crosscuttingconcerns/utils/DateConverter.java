package nl.knaake.erik.crosscuttingconcerns.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Offers functionality to convert a Date to a String and backwards, offers this with precision to days or to seconds
 * Also allows to do basic maths on dates
 */
public class DateConverter {
    private final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private final static DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMANY);

    /**
     * Converts the given Date to its string representation accurate to days
     * @param date Date to convert
     * @return String representation of the date accurate to days
     */
    public static String convertDateToString(Date date) {
        String result = null;
        if(date != null) {
            result = dateFormat.format(date);
        }
        return result;
    }

    /**
     * Converts a date to its string representation accurate to seconds
     * @param date Date to convert
     * @return string representation of the given date accurate to seconds
     */
    public static String convertDateToDateTimeString(Date date) {
        String result = null;
        if(date != null) {
            result = dateTimeFormat.format(date);
        }
        return result;
    }

    /**
     * Converts a string representation of date to a date accurate to seconds
     * @param date String to convert
     * @return Date version of the given string or null if it can't be parsed
     */
    public static Date convertStringToDateTime(String date) {
        Date result = null;
        try {
            result = dateTimeFormat.parse(date);
        }
        catch (ParseException e) {
            return result;
        }
        return result;
    }

    /**
     * Adds a given amount of minutes to the given date
     * @param date Date to add minutes to
     * @param minutes Amount of minutes to add to the date
     * @return Date with the added minutes
     */
    public static Date addMinutes(Date date, int minutes) {
        return DateUtils.addMinutes(date, minutes);
    }

    /**
     * Rounds a date to its nearest interval that is a given amount of minutes large
     * @param dateToRound Date to round to a interval
     * @param minuteIntervalToRoundTo Size of the intervals in minutes should be an interger in range: [1, 59]
     * @return Rounded date
     */
    public static Date roundToNearestMinutes(Date dateToRound, int minuteIntervalToRoundTo) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateToRound);
        c.set(Calendar.MINUTE, Math.round(c.get(Calendar.MINUTE) / minuteIntervalToRoundTo) * minuteIntervalToRoundTo);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
}
