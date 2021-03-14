package de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig;


import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.HashSet;
import java.util.Set;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.LongVariable"
})
public class LetzteTutorTermineService {

  private static final TemporalAmount ONE_WEEK = Duration.ofDays(7);

  private final transient UbungswocheConfigRepo ubWoConfRepo;

  public LetzteTutorTermineService(final UbungswocheConfigRepo ubWoConfRepo) {
    this.ubWoConfRepo = ubWoConfRepo;
  }

  public Set<TutorTermin> getNeueTutorTermine(final String anSchlussdatum,
                                              final String anSchlusszeit) {
    final var maybeUbWoConf
        = ubWoConfRepo.findByHighestId();

    Set<TutorTermin> neueTermine;
    if (maybeUbWoConf.isPresent()) {
      final Set<TutorTermin> tutorTermine =
          maybeUbWoConf.get().getTutorTermine();
      final var anmeldeSchluss
          = DateParseHelper.mergeDateTimeStrings(
          anSchlussdatum,
          anSchlusszeit
      );

      neueTermine = new HashSet<>(tutorTermine);

      updateDates(neueTermine, anmeldeSchluss);
    } else {
      neueTermine = new HashSet<>();
    }

    return neueTermine;
  }

  private void updateDates(final Set<TutorTermin> tutorTermine,
                           final LocalDateTime anmeldeSchluss) {
    while (notAllTermineAreAfterAnmeldeschluss(
        tutorTermine, anmeldeSchluss)) {
      addOneWeekToDate(tutorTermine);
    }
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private boolean notAllTermineAreAfterAnmeldeschluss(
      final Set<TutorTermin> tutorTermine,
      final LocalDateTime anmeldeSchluss) {
    boolean allAreAfterAnSchluss = true;

    for (final var tutorTermin : tutorTermine) {
      if (tutorTermin.getZeit().isBefore(anmeldeSchluss)) {
        allAreAfterAnSchluss = false;
        break;
      }
    }

    return !allAreAfterAnSchluss;
  }

  private void addOneWeekToDate(final Set<TutorTermin> tutorTermine) {
    tutorTermine.forEach(t -> t.setZeit(t.getZeit().plus(ONE_WEEK)));
  }

}