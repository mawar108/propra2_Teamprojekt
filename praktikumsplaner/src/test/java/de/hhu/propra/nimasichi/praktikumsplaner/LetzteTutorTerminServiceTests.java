package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.LetzteTutorTermineService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LetzteTutorTerminServiceTests {

  @Test
  @DisplayName("Das Repo ist leer, es werden keine Tutor Termine zuruckgegeben")
  public void testLeeresRepository() {
    var repo = mock(UbungswocheConfigRepo.class);
    when(repo.findByHighestId()).thenReturn(Optional.empty());

    LetzteTutorTermineService ttService = new LetzteTutorTermineService(repo);

    Set<TutorTermin> neueTutorTermine
        = ttService.getNeueTutorTermine(mock(ConfigParamsForm.class));

    assertThat(neueTutorTermine).isEmpty();
  }

  @Test
  @DisplayName("Im Repo wird ein Tutor Termin gefunden, das Datum wird um eine Wochen verschoben")
  public void testEineWocheVerschieben() {
    var repo = mock(UbungswocheConfigRepo.class);

    var alteTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 1, 10, 30))
    );

    var config = mock(PraktischeUbungswocheConfig.class);
    when(config.getTutorTermine()).thenReturn(alteTermine);
    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    ConfigParamsForm params = mockParamsForm("2020-01-03", "00:00");

    LetzteTutorTermineService ttService = new LetzteTutorTermineService(repo);

    Set<TutorTermin> neueTermine = ttService.getNeueTutorTermine(params);

    var expectedTermin = new TutorTermin("Max", LocalDateTime.of(2020, 1, 8, 10, 30));
    assertThat(neueTermine).contains(expectedTermin);
  }

  @Test
  @DisplayName("Ein Termin der letzten Woche ist vor Anmeldeschluss der neuen Woche, " +
      "trotzdem werden alle Termine um eine Woche verschoben")
  public void testEineWocheZweiTermine() {
    var repo = mock(UbungswocheConfigRepo.class);

    var alteTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 1, 10, 30)),
        new TutorTermin("Peter", LocalDateTime.of(2020, 1, 3, 10, 30))
    );

    var config = mock(PraktischeUbungswocheConfig.class);
    when(config.getTutorTermine()).thenReturn(alteTermine);

    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    ConfigParamsForm params = mockParamsForm("2020-01-02", "00:00");
    LetzteTutorTermineService ttService = new LetzteTutorTermineService(repo);

    Set<TutorTermin> neueTermine = ttService.getNeueTutorTermine(params);

    var expectedTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 8, 10, 30)),
        new TutorTermin("Peter", LocalDateTime.of(2020, 1, 10, 10, 30))
    );
    assertThat(neueTermine).containsAll(expectedTermine);
  }

  @Test
  @DisplayName("Wegen Ferien fallt eine Ubungswoche aus, alle Termine werden um 2 Wochen verschoben.")
  // Im vergleich zu letzten Test hat sich nur der Anmeldeschluss geandert!
  public void testZweiWochenZweiTermine() {
    var repo = mock(UbungswocheConfigRepo.class);

    var alteTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 1, 10, 30)),
        new TutorTermin("Peter", LocalDateTime.of(2020, 1, 3, 10, 30))
    );

    var config = mock(PraktischeUbungswocheConfig.class);
    when(config.getTutorTermine()).thenReturn(alteTermine);

    when(repo.findByHighestId()).thenReturn(Optional.of(config));

    ConfigParamsForm params = mockParamsForm("2020-01-09", "00:00");
    LetzteTutorTermineService ttService = new LetzteTutorTermineService(repo);

    Set<TutorTermin> neueTermine = ttService.getNeueTutorTermine(params);

    var expectedTermine = Set.of(
        new TutorTermin("Max", LocalDateTime.of(2020, 1, 15, 10, 30)),
        new TutorTermin("Peter", LocalDateTime.of(2020, 1, 17, 10, 30))
    );
    assertThat(neueTermine).containsAll(expectedTermine);
  }

  private ConfigParamsForm mockParamsForm(String anmeldeSchlussDatum, String anmeldeSchlusszeit) {
    ConfigParamsForm params = mock(ConfigParamsForm.class);
    when(params.getAnSchlussdatum()).thenReturn(anmeldeSchlussDatum);
    when(params.getAnSchlusszeit()).thenReturn(anmeldeSchlusszeit);
    return params;
  }

}