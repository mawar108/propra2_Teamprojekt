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

    @SuppressWarnings("PMD.LawOfDemeter")
    public static LocalDate stringToLocalDate(final String date) {
        final var formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withLocale(Locale.GERMANY);
        return LocalDate.parse(date, formatter);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public static LocalTime stringToLocalTime(final String time) {
        final var formatter =
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
        final List<DayOfWeek> daysOfWeek = new ArrayList<>();

        for (int i = 1; i <= weekDay; ++i) {
            daysOfWeek.add(DayOfWeek.of(i));
        }

        return daysOfWeek;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public static String formatTime(final LocalDateTime ldtime) {
        return abbreviateDayOfWeek(
                ldtime.getDayOfWeek())
                + " " + ldtime.toLocalTime().toString();
    }

    private static String abbreviateDayOfWeek(final DayOfWeek day) {
        String abbreviation;
        switch (day) {
            case MONDAY: abbreviation = "Mo";
                break;
            case TUESDAY: abbreviation = "Di";
                break;
            case WEDNESDAY: abbreviation = "Mi";
                break;
            case THURSDAY: abbreviation = "Do";
                break;
            case FRIDAY: abbreviation = "Fr";
                break;
            case SATURDAY: abbreviation = "Sa";
                break;
            default: abbreviation = "So";
                break;
        }
        return abbreviation;
    }
}
