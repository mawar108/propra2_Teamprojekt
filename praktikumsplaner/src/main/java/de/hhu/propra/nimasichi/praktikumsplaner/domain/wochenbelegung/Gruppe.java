package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({
    "PMD.ShortVariable",
    "PMD.DefaultPackage"
})
class Gruppe {

  @Id
  private Long id;

  private String gruppenName;
  private String tutorenName;
  private Set<Student> mitglieder;

  /* default */ Gruppe(final String tutorenName) {
    this.tutorenName = tutorenName;
    this.gruppenName = "";
    this.mitglieder  = new HashSet<>();
  }

  /* default */ boolean hatRestplatze(final int maxPersonen) {
    return !mitglieder.isEmpty()
        && mitglieder.size() < maxPersonen;
  }

  public void addMitglied(final String login) {
    mitglieder.add(new Student(login));
  }

}
