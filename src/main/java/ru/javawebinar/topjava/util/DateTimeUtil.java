package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalTime toStartFilterLocalTime(String data, String time){
        data = (data == null) ? "1980-01-01" : data;
        time = (time == null || time.equals("")) ? "00:00" : time;
        return LocalTime.parse(data + " " + time, DATE_TIME_FORMATTER);
    }

    public static LocalTime toEndFilterLocalTime(String data, String time){
        data = (data == null) ? "2030-01-01" : data;
        time = (time == null || time.equals("")) ? "23:59" : time;
        return LocalTime.parse(data + " " + time, DATE_TIME_FORMATTER);
    }
}

