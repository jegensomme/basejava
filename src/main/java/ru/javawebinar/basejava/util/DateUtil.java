package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * gkislin
 * 20.07.2016
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/YYYY");

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        return NOW.isEqual(date) ? "Сейчас" : DATE_TIME_FORMATTER.format(date);
    }

    public static String getPeriod(LocalDate startDate, LocalDate endDate) {
        return format(startDate) + " - " + format(endDate);
    }

    public static boolean isAfterNow(LocalDate date) {
        return LocalDate.now().isBefore(date);
    }

    public static LocalDate getNullIfAfterNow(LocalDate date) {
        return isAfterNow(date) ? null : date;
    }
}