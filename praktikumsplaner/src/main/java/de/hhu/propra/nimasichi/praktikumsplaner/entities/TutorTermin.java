package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.utility.DateParseHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

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

  public String toParseable() {
    return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + name;
  }

}
