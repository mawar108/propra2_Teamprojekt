package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.annotations.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@ToString
@AggregateRoot
@NoArgsConstructor
@AllArgsConstructor
public final class Wochenbelegung {

  @Id
  private Long id;
  private Set<Zeitslot> zeitslots = new HashSet<>();

  private Wochenbelegung(Set<Zeitslot> zeitslots) {
    this.zeitslots = zeitslots;
  }

  public static Wochenbelegung fromConfig(final PraktischeUbungswocheConfig config) {
    return new Wochenbelegung(
        config.parseTutorTerminToZeitslots());
  }
}
