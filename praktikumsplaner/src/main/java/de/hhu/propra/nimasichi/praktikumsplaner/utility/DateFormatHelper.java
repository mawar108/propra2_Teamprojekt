package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public final class DateFormatHelper {
  private DateFormatHelper() { }

  @SuppressWarnings("PMD.LawOfDemeter")
  public static String formatTime(final LocalDateTime ldtime) {
    return ldtime.toLocalDate().toString()
        + " " + abbreviateDayOfWeek(ldtime.getDayOfWeek())
        + " " + ldtime.toLocalTime().toString();
  }

  private static String abbreviateDayOfWeek(final DayOfWeek day) {
    String abbreviation;
    switch (day) {
      case MONDAY:
        abbreviation = "Mo";
        break;
      case TUESDAY:
        abbreviation = "Di";
        break;
      case WEDNESDAY:
        abbreviation = "Mi";
        break;
      case THURSDAY:
        abbreviation = "Do";
        break;
      case FRIDAY:
        abbreviation = "Fr";
        break;
      case SATURDAY:
        abbreviation = "Sa";
        break;
      default:
        abbreviation = "So";
        break;
    }
    return abbreviation;
  }
}
