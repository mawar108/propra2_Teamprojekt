package de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.ShortVariable")
public class TutorTermin {

  @Id
  private Long id;

  private String name;
  private LocalDateTime zeit;

  public TutorTermin(final String name,
                     final LocalDateTime zeit) {
    this.name = name;
    this.zeit = zeit;
  }

  public static TutorTermin fromParseable(final String fmt) {
    final var parts = fmt.split(";");
    final var time =
        LocalDateTime.of(DateParseHelper.stringToLocalDate(parts[0]),
            DateParseHelper.stringToLocalTime(parts[1]));

    return new TutorTermin(parts[2], time);
  }

  public static TutorTermin from(final String tutorenName,
                          final String slotZeit,
                          final String slotDatum) {
    return fromParseable(slotDatum + ";" + slotZeit + ";" + tutorenName);
  }

  public LocalDate getLocalDate() {
    return zeit.toLocalDate();
  }

  public LocalTime getLocalTime() {
    return zeit.toLocalTime();
  }

}
