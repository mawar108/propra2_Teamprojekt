package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Gruppe {
  @Id
  private Long id;

  private String gruppenName;
  private String tutorenName;
  private Set<Student> mitglieder;

  Gruppe(final String tutorenName) {
    this.tutorenName = tutorenName;
  }

  Gruppe(final String gruppenName,
                final String tutorenName,
                final Set<Student> mitglieder) {
    this.gruppenName = gruppenName;
    this.tutorenName = tutorenName;
    this.mitglieder = mitglieder;
  }
}
