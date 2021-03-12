package de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin.fromParseable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@AggregateRoot
@SuppressWarnings("PMD.LawOfDemeter")
public class PraktischeUbungswocheConfig {
  @Id
  private Long id;

  private LocalDateTime anmeldestart;
  private LocalDateTime anmeldeschluss;
  private String name;
  private Set<TutorTermin> tutorTermine = new HashSet<>();
  private int modus;
  private int minPersonen;
  private int maxPersonen;

  public void addZeitslot(final TutorTermin zeitslot) {
    tutorTermine.add(zeitslot);
  }

  public void removeZeitslot(final TutorTermin tutorTermin) {
    tutorTermine.remove(tutorTermin);
  }

  public Set<Zeitslot> parseTutorTerminToZeitslots() {
    return tutorTermine.stream()
            .collect(Collectors.groupingBy(TutorTermin::getZeit))
            .values()
            .stream()
            .map(tts -> Wochenbelegung.zeitslotFromTutorTermine(tts, minPersonen, maxPersonen))
            .collect(Collectors.toSet());
  }

  public static TutorTermin tutorTerminFrom(final String tutorenName,
                                            final String slotZeit,
                                            final String slotDatum) {

    return TutorTermin.from(slotDatum, slotZeit, tutorenName);
  }

  public static TutorTermin tutorTerminFromParseable(final String fmt) {
    return TutorTermin.fromParseable(fmt);
  }

}
