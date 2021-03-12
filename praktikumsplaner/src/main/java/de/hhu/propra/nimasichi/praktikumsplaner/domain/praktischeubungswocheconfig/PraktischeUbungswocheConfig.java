package de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.annotations.AggregateRoot;
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
            .map(tts -> Zeitslot.fromTutorTermin(tts, minPersonen, maxPersonen))
            .collect(Collectors.toSet());
  }
}
