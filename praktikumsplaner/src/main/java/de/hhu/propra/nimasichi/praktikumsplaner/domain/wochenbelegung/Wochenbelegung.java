package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AggregateRoot
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("PMD.ShortVariable")
public final class Wochenbelegung {

  @Id
  private Long id;
  private Set<Zeitslot> zeitslots;

  private Wochenbelegung(final Set<Zeitslot> zeitslots) {
    this.zeitslots = zeitslots;
  }

  public List<Zeitslot> getZeitslotsWithRestplatze() {
    return zeitslots.stream()
        .filter(Zeitslot::hatRestplatze)
        .collect(Collectors.toList());
  }

  public static Wochenbelegung fromConfig(final PraktischeUbungswocheConfig config) {
    return new Wochenbelegung(
        config.parseTutorTerminToZeitslots());
  }

  public static Zeitslot zeitslotFromTutorTermine(final List<TutorTermin> tutorTermine,
                                                  final int minPersonen,
                                                  final int maxPersonen) {
    return Zeitslot.fromTutorTermine(tutorTermine, minPersonen, maxPersonen);
  }

  public static void addToZeitslot(Zeitslot zeitslot, String login) {
    zeitslot.addToGruppe(login);
  }

  public static boolean zeitslotHatRestplatze(Zeitslot zeitslot) {
    return zeitslot.hatRestplatze();
  }

}
