package de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
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

  static TutorTermin fromParseable(final String fmt) {
    final var parts = fmt.split(";");

    final var time =
        LocalDateTime.of(DateParseHelper.stringToLocalDate(parts[0]),
            DateParseHelper.stringToLocalTime(parts[1]));

    return new TutorTermin(parts[2], time);
  }

  static TutorTermin from(final String tutorenName,
                                 final String slotZeit,
                                 final String slotDatum) {
    return fromParseable(slotDatum + ";" + slotZeit + ";" + tutorenName);
  }

  String toParseable() {
    return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + name;
  }

}
