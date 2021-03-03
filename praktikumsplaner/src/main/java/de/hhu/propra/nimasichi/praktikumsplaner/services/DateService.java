package de.hhu.propra.nimasichi.praktikumsplaner.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateService {
    public static LocalDate stringToLocalDate(String datum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.GERMANY);
        return LocalDate.parse(datum, formatter);
    }

    public static LocalTime stringToLocalTime(String zeit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        formatter = formatter.withLocale(Locale.GERMANY);
        return LocalTime.parse(zeit, formatter);
    }

    public static LocalDateTime mergeDateTimeStrings(String datum, String zeit) {
        return LocalDateTime.of(stringToLocalDate(datum), stringToLocalTime(zeit));
    }

    public static List<DayOfWeek> getDaysOfWeekUntil(int weekDay) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        for (int i = 1; i <= weekDay; ++i) {
            daysOfWeek.add(DayOfWeek.of(i));
        }

        return daysOfWeek;
    }
}
