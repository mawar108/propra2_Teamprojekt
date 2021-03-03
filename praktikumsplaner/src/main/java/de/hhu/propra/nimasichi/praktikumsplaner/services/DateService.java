package de.hhu.propra.nimasichi.praktikumsplaner.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class DateService {
    private DateService() { }

    public static LocalDate stringToLocalDate(final String date) {
        var formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withLocale(Locale.GERMANY);
        return LocalDate.parse(date, formatter);
    }

    public static LocalTime stringToLocalTime(final String time) {
        var formatter =
                DateTimeFormatter.ofPattern("HH:mm")
                        .withLocale(Locale.GERMANY);
        return LocalTime.parse(time, formatter);
    }

    public static LocalDateTime mergeDateTimeStrings(final String date,
                                                     final String time) {
        return LocalDateTime.of(
                stringToLocalDate(date),
                stringToLocalTime(time)
        );
    }

    public static List<DayOfWeek> getDaysOfWeekUntil(final int weekDay) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        for (int i = 1; i <= weekDay; ++i) {
            daysOfWeek.add(DayOfWeek.of(i));
        }

        return daysOfWeek;
    }

    public static String formatTime(final LocalDateTime ldtime) {
        return abbreviateDayOfWeek(
                ldtime.getDayOfWeek())
                + " " + ldtime.toLocalTime().toString();
    }

    private static String abbreviateDayOfWeek(final DayOfWeek day) {
        switch (day) {
            case MONDAY: return "Mo";
            case TUESDAY: return "Di";
            case WEDNESDAY: return "Mi";
            case THURSDAY: return "Do";
            case FRIDAY: return "Fr";
            case SATURDAY: return "Sa";
            default: return "So";
        }
    }
}
