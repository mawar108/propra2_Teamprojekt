package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({
    "PMD.ShortVariable",
    "PMD.DefaultPackage",
    "PMD.CommentDefaultAccessModifier",
    "PMD.LawOfDemeter"
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

  public boolean isLeer() {
    return mitglieder.isEmpty();
  }

  public boolean belegt(final int maxPersonen) {
    return mitglieder.size() == maxPersonen;
  }

  public void addMitglieder(final List<Student> students) {
    mitglieder.addAll(students);
  }

  Set<String> getMitgliederHandles() {
    return mitglieder
        .stream()
        .map(Student::getGithubHandle)
        .collect(Collectors.toSet());
  }
}
