package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.dto.GruppeDto;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.GruppenVerteilungsHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@AggregateRoot
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({
        "PMD.ShortVariable",
        "PMD.DataflowAnomalyAnalysis",
        "PMD.LawOfDemeter",
        "PMD.DefaultPackage",
        "PMD.LongVariable"
})
public class Zeitslot {
  @Id
  private Long id;

  private LocalDateTime ubungsAnfang;
  private Set<Gruppe> gruppen;
  private int minPersonen;
  private int maxPersonen;
  private Set<AngemeldeterStudent> angemeldeteStudenten;

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
          final List<String> mitglieder, final String gruppenname) {
    final var rand = new Random();
    final var emptyGruppen
            = gruppen.stream()
            .filter(Gruppe::isLeer)
            .collect(Collectors.toList());
    final var idx = rand.nextInt(emptyGruppen.size());
    final var selectedGruppe = emptyGruppen.get(idx);

    selectedGruppe.setGruppenName(gruppenname);
    mitglieder.forEach(selectedGruppe::addMitglied);
  }

  public boolean isInAnyGruppe(final String login) {
    boolean inGruppe = false;

    for (final var gruppe : gruppen) {
      if (gruppe.containsMitglied(login)) {
        inGruppe = true;
        break;
      }
    }

    return inGruppe;
  }

  public boolean minEineFreieGruppe() {
    boolean frei = false;

    for (final var gruppe : gruppen) {
      if (gruppe.isLeer()) {
        frei = true;
        break;
      }
    }

    return frei;
  }

  public boolean istKomplettBelegt() {
    boolean belegt = true;

    for (final var gruppe : gruppen) {
      if (!gruppe.belegt(maxPersonen)) {
        belegt = false;
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
    angemeldeteStudenten.add(new AngemeldeterStudent(login));
  }

  public void gruppenErstellen() {
    final var studenten = angemeldeteStudenten.stream()
            .map(s -> new Student(s.getGithubHandle()))
            .collect(Collectors.toList());

    final List<List<Student>> partitions = GruppenVerteilungsHelper.partition(
            studenten,
            minPersonen,
            maxPersonen);

    final ArrayList<Gruppe> gruppen = new ArrayList<>(this.gruppen);

    for (int i = 0; i < partitions.size(); i++) {
      gruppen.get(i).addMitglieder(partitions.get(i));
      gruppen.get(i).setGruppenName(String.valueOf(i + 1));
    }
    angemeldeteStudenten.clear();
  }

  public Set<GruppeDto> getGruppenDto() {
    return gruppen.stream()
            .map(g -> new GruppeDto(g.getGruppenName(),
                    g.getMitgliederHandles(),
                    g.getTutorenName()))
            .collect(Collectors.toSet());
  }

  public void addMitgliedToGruppe(final String gruppenName, final String studentenName) {
    final var maybeGruppe = getGruppeByName(gruppenName);
    if (maybeGruppe.isPresent()) {
      maybeGruppe.get().addMitglied(studentenName);
    }
  }

  public void deleteMitgliedFromGruppe(final String gruppenName, final String studentenName) {
    final var maybeGruppe = getGruppeByName(gruppenName);
    if (maybeGruppe.isPresent()) {
      maybeGruppe.get().deleteMitglied(studentenName);
    }
  }

  private Optional<Gruppe> getGruppeByName(final String gruppenName) {
    final List<Gruppe> gruppe = gruppen.stream()
        .filter(g -> g.getGruppenName().equals(gruppenName))
        .collect(Collectors.toList());

    Optional<Gruppe> maybeGruppe;
    if (gruppe.isEmpty()) {
      maybeGruppe = Optional.empty();
    } else {
      maybeGruppe = Optional.of(gruppe.get(0));
    }

    return maybeGruppe;
  }
}
