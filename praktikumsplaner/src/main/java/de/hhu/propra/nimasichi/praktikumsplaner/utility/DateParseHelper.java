package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateParseHelper {

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

}
