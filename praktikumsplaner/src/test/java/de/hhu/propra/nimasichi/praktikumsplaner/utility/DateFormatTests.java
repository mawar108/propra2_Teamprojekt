package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.utility.DateFormatHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DateFormatTests {
  private DateFormatTests() { }

  @Test
  void testFirstOfJanuary() {
    final var dtime = LocalDateTime.of(2021, 1, 1, 0, 0);
    assertThat(DateFormatHelper.formatTime(dtime)).isEqualTo("Fr 01.01. 00:00");
  }

  @Test
  void testLastOfFebruary() {
    final var dtime = LocalDateTime.of(2021, 2, 28, 12, 0);
    assertThat(DateFormatHelper.formatTime(dtime)).isEqualTo("So 28.02. 12:00");
  }

  @Test
  void testNewYearsEve() {
    final var dtime = LocalDateTime.of(2021, 12, 31, 23, 59);
    assertThat(DateFormatHelper.formatTime(dtime)).isEqualTo("Fr 31.12. 23:59");
  }
}
