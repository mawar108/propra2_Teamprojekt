package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@AggregateRoot
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({
    "PMD.ShortVariable",
    "PMD.DataflowAnomalyAnalysis",
    "PMD.LawOfDemeter",
    "PMD.DefaultPackage"
})
public class Zeitslot {
  @Id
  private Long id;

  private LocalDateTime ubungsAnfang;
  private Set<Gruppe> gruppen;
  private int minPersonen;
  private int maxPersonen;
  private Set<Student> angemeldeteStudenten;


  private long ubungswocheConfig;

  public static Zeitslot fromTutorTermine(final List<TutorTermin> tutorTermine,
                                          final int minPersonen,
                                          final int maxPersonen,
                                          final long configId) {

    final var zeitslot = new Zeitslot();
    final var gruppen
        = tutorTermine.stream()
        .map(x -> new Gruppe(x.getName()))
        .collect(Collectors.toSet());

    zeitslot.setUbungsAnfang(tutorTermine.get(0).getZeit());
    zeitslot.setGruppen(gruppen);
    zeitslot.setMinPersonen(minPersonen);
    zeitslot.setMaxPersonen(maxPersonen);
    zeitslot.setUbungswocheConfig(configId);

    return zeitslot;
  }

  public void addMitgliederToRandomGroup(
      final List<String> mitglieder) {
    final var rand = new Random();
    final var emptyGruppen
        = gruppen.stream()
        .filter(Gruppe::isLeer)
        .collect(Collectors.toList());
    final var idx = rand.nextInt(emptyGruppen.size());
    final var selection = emptyGruppen.get(idx);

    mitglieder.forEach(selection::addMitglied);
  }

  public boolean minEineFreieGruppe() {
    boolean belegt = false;

    for (final var gruppe : gruppen) {
      if (gruppe.isLeer()) {
        belegt = true;
        break;
      }
    }

    return belegt;
  }

  public boolean istKomplettBelegt() {
    boolean belegt = false;

    for (final var gruppe : gruppen) {
      if (gruppe.belegt(maxPersonen)) {
        belegt = true;
        break;
      }
    }

    return belegt;
  }

  public boolean hatRestplatze() {
    boolean hatRestplatze = false;

    for (final var gruppe : gruppen) {
      if (gruppe.hatRestplatze(maxPersonen)) {
        hatRestplatze = true;
        break;
      }
    }

    return hatRestplatze;
  }

  public void addToGruppe(final String login) {
    for (final var gruppe : gruppen) {
      if (gruppe.hatRestplatze(maxPersonen)) {
        gruppe.addMitglied(login);
        return;
      }
    }
  }

  public boolean hatFreienGruppenplatz() {
    boolean frei = false;

    for (final var gruppe : gruppen) {
      if (gruppe.isLeer()) {
        frei = true;
        break;
      }
    }

    return frei;
  }

  public void addToAngemeldeteStudenten(final String login) {
    angemeldeteStudenten.add(new Student(login));
  }

  public void gruppeErstellen() {

  }




}
