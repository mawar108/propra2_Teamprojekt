package de.hhu.propra.nimasichi.praktikumsplaner.service;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.LetzteTutorTermineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LetzteTutorTerminServiceTests {

  private LetzteTutorTerminServiceTests() { }

  @Test
  @DisplayName("Das Repo ist leer, es werden keine Tutor Termine zuruckgegeben")
  public void testLeeresRepository() {
    final var repo = mock(UbungswocheConfigRepo.class);
    final var ttService = new LetzteTutorTermineService(repo);

    when(repo.findByHighestId()).thenReturn(Optional.empty());

    final var neueTutorTermine
        = ttService.getNeueTutorTermine("2020-01-01", "00:00");

    assertThat(neueTutorTermine).isEmpty();
  }

  @Test
  @DisplayName("Im Repo wird ein Tutor Termin gefunden,"
      + "das Datum wird um eine Wochen verschoben")
  public void testEineWocheVerschieben() {
    final var anmeldeSchlussdatum = "2020-01-03";
    final var anmeldeSchlusszeit =  "00:00";
    final var repo = mock(UbungswocheConfigRepo.class);
    final var alteTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 1, 10,
            30))
    );
    final var config = mock(UbungswocheConfig.class);

    when(config.getTutorTermine()).thenReturn(alteTermine);
    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    final var ttService = new LetzteTutorTermineService(repo);
    final var neueTermine
        = ttService.getNeueTutorTermine(anmeldeSchlussdatum,
        anmeldeSchlusszeit);
    final var expectedTermin
        = new TutorTermin(
        "Max",
        LocalDateTime.of(
            2020, 1, 8, 10, 30));

    assertThat(neueTermine).contains(expectedTermin);
  }

  @Test
  @DisplayName("Ein Termin der letzten "
      + "Woche ist vor Anmeldeschluss der neuen Woche, "
      + "trotzdem werden alle Termine um eine Woche verschoben")
  public void testEineWocheZweiTermine() {
    final var anmeldeSchlussdatum = "2020-01-02";
    final var anmeldeSchlusszeit =  "00:00";
    final var repo = mock(UbungswocheConfigRepo.class);
    final var alteTermine = Set.of(
        new TutorTermin("Max",
            LocalDateTime.of(2020, 1, 1, 10,
                30)),
        new TutorTermin("Peter",
            LocalDateTime.of(2020, 1, 3, 10,
                30))
    );
    final var config = mock(UbungswocheConfig.class);

    when(config.getTutorTermine()).thenReturn(alteTermine);
    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    final var ttService = new LetzteTutorTermineService(repo);
    final var neueTermine
        = ttService.getNeueTutorTermine(
        anmeldeSchlussdatum, anmeldeSchlusszeit);
    final var expectedTermine = Set.of(
        new TutorTermin("Max",
            LocalDateTime.of(2020, 1, 8, 10,
                30)),
        new TutorTermin("Peter",
            LocalDateTime.of(2020, 1, 10, 10,
                30))
    );

    assertThat(neueTermine).containsAll(expectedTermine);
  }

  @Test
  @DisplayName("Wegen Ferien fallt eine Ubungswoche aus, "
      + "alle Termine werden um 2 Wochen verschoben.")
  // Im vergleich zum letzten Test hat sich nur der Anmeldeschluss geändert!
  public void testZweiWochenZweiTermine() {
    final var repo = mock(UbungswocheConfigRepo.class);
    final var alteTermine = Set.of(
        new TutorTermin("Max",
            LocalDateTime.of(2020, 1, 1, 10,
                30)),
        new TutorTermin("Peter",
            LocalDateTime.of(2020, 1, 3, 10,
                30))
    );
    final var config
        = mock(UbungswocheConfig.class);

    when(config.getTutorTermine()).thenReturn(alteTermine);
    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    final var anmeldeSchlussdatum = "2020-01-09";
    final var anmeldeSchlusszeit =  "00:00";
    final var ttService = new LetzteTutorTermineService(repo);
    final var neueTermine
        = ttService.getNeueTutorTermine(anmeldeSchlussdatum, anmeldeSchlusszeit);
    final var expectedTermine = Set.of(
        new TutorTermin("Max",
            LocalDateTime.of(2020, 1, 15, 10, 30)),
        new TutorTermin("Peter",
            LocalDateTime.of(2020, 1, 17, 10, 30))
    );

    assertThat(neueTermine).containsAll(expectedTermine);
  }
}
