package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({
    "PMD.DefaultPackage",
    "PMD.CommentDefaultAccessModifier",
    "PMD.ShortVariable",
    "PMD.LawOfDemeter"
})
public class Zeitslot {
  @Id
  private Long id;

  private LocalDateTime ubungsAnfang;
  private Set<Gruppe> gruppen;
  private int minPersonen;
  private int maxPersonen;

  public static Zeitslot fromTutorTermin(final List<TutorTermin> tutorTermine,
                                         final int minPersonen,
                                         final int maxPersonen) {
    final var zeitslot = new Zeitslot();
    final var gruppen
        = tutorTermine.stream()
        .map(x -> new Gruppe(x.getName()))
        .collect(Collectors.toSet());

    zeitslot.setUbungsAnfang(tutorTermine.get(0).getZeit());
    zeitslot.setGruppen(gruppen);
    zeitslot.setMinPersonen(minPersonen);
    zeitslot.setMaxPersonen(maxPersonen);

    return zeitslot;
  }

  public boolean alleGruppenAngemeldet() {
    boolean belegt = false;
    for (var gruppe : gruppen) {
      if (!gruppe.getMitglieder().isEmpty()) {
        belegt = true;
        break;
      }
    }
    return belegt;
  }

}
