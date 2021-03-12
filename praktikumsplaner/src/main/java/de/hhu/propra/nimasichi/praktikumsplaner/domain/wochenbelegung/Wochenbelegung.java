package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import de.hhu.propra.nimasichi.praktikumsplaner.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.PraktischeUbungswocheConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

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

  public static Wochenbelegung fromConfig(final PraktischeUbungswocheConfig config) {
    return new Wochenbelegung(
        config.parseTutorTerminToZeitslots());
  }

}
