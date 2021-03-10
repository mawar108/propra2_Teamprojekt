package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@AggregateRoot
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.LawOfDemeter"})
public class PraktischeUbungswocheConfig {
  @Id
  private Long id;

  private LocalDateTime anmeldestart;
  private LocalDateTime anmeldeschluss;
  private String name;
  private Set<TutorTermin> tutorTermine = new HashSet<>();
  private int modus;
  private int minPersonen;
  private int maxPersonen;


  public static PraktischeUbungswocheConfig makeConfigAndFillZeiten(
      final ConfigParamsForm params,
      final Set<TutorTermin> tutorenZeiten) {
    final var praUbungswocheCfg =
        new PraktischeUbungswocheConfig();

    praUbungswocheCfg.setName(params.getName());

    praUbungswocheCfg.setAnmeldestart(
        DateService
            .mergeDateTimeStrings(
                params.getAnStartdatum(),
                params.getAnStartzeit())
    );

    praUbungswocheCfg.setAnmeldeschluss(
        DateService
            .mergeDateTimeStrings(
                params.getAnSchlussdatum(),
                params.getAnSchlusszeit())
    );

    praUbungswocheCfg
        .setModus(params.getModus());
    praUbungswocheCfg
        .setMinPersonen(params.getMinPersonen());
    praUbungswocheCfg
        .setMaxPersonen(params.getMaxPersonen());

    praUbungswocheCfg.setTutorTermine(tutorenZeiten);
    return praUbungswocheCfg;
  }

  public void addZeitslot(final TutorTermin zeitslot) {
    tutorTermine.add(zeitslot);
  }

  public void removeZeitslot(final TutorTermin tutorTermin) {
    tutorTermine.remove(tutorTermin);
  }

  public Set<Zeitslot> parseTutorTerminToZeitslots() {
    return tutorTermine.stream()
            .collect(Collectors.groupingBy(TutorTermin::getZeit))
            .values()
            .stream()
            .map(tts -> Zeitslot.fromTutorTermin(tts, minPersonen, maxPersonen))
            .collect(Collectors.toSet());
  }
}
