package de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@AggregateRoot
@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.ShortVariable"
})
public class UbungswocheConfig {
  @Id
  private Long id;

  private LocalDateTime anmeldestart;
  private LocalDateTime anmeldeschluss;
  private String name;
  private Set<TutorTermin> tutorTermine = new HashSet<>();
  private int modus;
  private int minPersonen;
  private int maxPersonen;
  private boolean reposErstellt;

  public Set<Zeitslot> parseTutorTerminToZeitslots() {
    return tutorTermine.stream()
        .collect(Collectors.groupingBy(TutorTermin::getZeit))
        .values()
        .stream()
        .map(tts -> Zeitslot.fromTutorTermine(tts, minPersonen, maxPersonen, id))
        .collect(Collectors.toSet());
  }

  public boolean isAktuell() {
    return anmeldestart.isBefore(LocalDateTime.now())
        && anmeldeschluss.isAfter(LocalDateTime.now());
  }


  public boolean anmeldeschlussAbgelaufen() {
    return anmeldeschluss.isBefore(LocalDateTime.now());
  }

  public static TutorTermin tutorTerminFrom(final String tutorenName,
                                            final String slotZeit,
                                            final String slotDatum) {
    return TutorTermin.from(tutorenName, slotZeit, slotDatum);
  }

  public static TutorTermin tutorTerminFromParseable(final String fmt) {
    return TutorTermin.fromParseable(fmt);
  }
}
