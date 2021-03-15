package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AggregateRoot
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({
    "PMD.ShortVariable",
    "PMD.LawOfDemeter",
    "PMD.DataflowAnomalyAnalysis"
})
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

  public void addGruppeToZeitslot(final Long zeitslotId,
                                  final Set<String> mitglieder) {
    final var maybeZeitslot = findZeitslotById(zeitslotId);
    if (maybeZeitslot.isPresent()) {
      final var zeitslot = maybeZeitslot.get();
      zeitslot.addMitgliederToRandomGroup(mitglieder);
    }
  }

  private Optional<Zeitslot> findZeitslotById(final Long zeitslotId) {
    Optional<Zeitslot> maybeZeitslot = Optional.empty();

    for (final var zeitslot : zeitslots) {
      if (zeitslot.getId().equals(zeitslotId)) {
        maybeZeitslot = Optional.of(zeitslot);
        break;
      }
    }

    return maybeZeitslot;
  }

  public static Wochenbelegung fromConfig(final UbungswocheConfig config) {
    return new Wochenbelegung(
        config.parseTutorTerminToZeitslots());
  }

  public static Zeitslot zeitslotFromTutorTermine(final List<TutorTermin> tutorTermine,
                                                  final int minPersonen,
                                                  final int maxPersonen) {
    return Zeitslot.fromTutorTermine(tutorTermine, minPersonen, maxPersonen);
  }

  public static void addToZeitslot(
      final Zeitslot zeitslot,
      final String login) {
    zeitslot.addToGruppe(login);
  }

  public static boolean zeitslotHatRestplatze(
      final Zeitslot zeitslot) {
    return zeitslot.hatRestplatze();
  }

}
