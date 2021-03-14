package de.hhu.propra.nimasichi.praktikumsplaner.services;


import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.HashSet;
import java.util.Set;

@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class LetzteTutorTermineService {

  private static final TemporalAmount ONE_WEEK = Duration.ofDays(7);

  private final transient UbungswocheConfigRepo ubWoConfRepo;

  public LetzteTutorTermineService(final UbungswocheConfigRepo ubWoConfRepo) {
    this.ubWoConfRepo = ubWoConfRepo;
  }

  public Set<TutorTermin> getNeueTutorTermine(final ConfigParamsForm params) {
    final var maybeUbWoConf = ubWoConfRepo.findByHighestId();

    Set<TutorTermin> neueTermine;
    if (maybeUbWoConf.isPresent()) {
      final Set<TutorTermin> tutorTermine =
          maybeUbWoConf.get().getTutorTermine();
      neueTermine = new HashSet<>(tutorTermine);

      final var anmeldeSchluss = DateParseHelper.mergeDateTimeStrings(
          params.getAnSchlussdatum(),
          params.getAnSchlusszeit()
      );
      updateDates(neueTermine, anmeldeSchluss);
    } else {
      neueTermine = new HashSet<>();
    }

    return neueTermine;
  }

  private void updateDates(final Set<TutorTermin> tutorTermine,
                           final LocalDateTime anmeldeSchluss) {
    while (notAllTermineAreAfterAnmeldeschluss(tutorTermine, anmeldeSchluss)) {
      addOneWeekToDate(tutorTermine);
    }
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private boolean notAllTermineAreAfterAnmeldeschluss(
      final Set<TutorTermin> tutorTermine,
      final LocalDateTime anmeldeSchluss) {
    boolean allAreAfterAs = true;

    for (final TutorTermin tutorTermin : tutorTermine) {
      if (tutorTermin.getZeit().isBefore(anmeldeSchluss)) {
        allAreAfterAs = false;
        break;
      }
    }

    return !allAreAfterAs;
  }

  private void addOneWeekToDate(final Set<TutorTermin> tutorTermine) {
    tutorTermine.forEach(t -> t.setZeit(t.getZeit().plus(ONE_WEEK)));
  }

}