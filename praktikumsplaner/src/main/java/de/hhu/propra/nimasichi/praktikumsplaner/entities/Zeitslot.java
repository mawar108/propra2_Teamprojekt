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
@SuppressWarnings({"PMD.DefaultPackage", "PMD.CommentDefaultAccessModifier"})
@AllArgsConstructor
@NoArgsConstructor
public class Zeitslot {
  @Id
  private Long id;

  private LocalDateTime ubungsAnfang;
  private Set<Gruppe> gruppen;
  private int minPersonen;
  private int maxPersonen;

  public static Zeitslot fromTutorTermin(List<TutorTermin> tutorTermine, int minPersonen, int maxPersonen) {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setUbungsAnfang(tutorTermine.get(0).getZeit());

    Set<Gruppe> gruppen = tutorTermine.stream()
        .map(x -> new Gruppe(x.getName()))
        .collect(Collectors.toSet());
    zeitslot.setGruppen(gruppen);

    zeitslot.setMinPersonen(minPersonen);
    zeitslot.setMaxPersonen(maxPersonen);

    return zeitslot;
  }
}
