package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorWochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorTerminRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("PMD.LawOfDemeter")
public final class TutorTerminService {

  private final transient TutorTerminRepo tzRepo;

  public TutorTerminService(final TutorTerminRepo repo) {
    this.tzRepo = repo;
  }

  public List<TutorTermin> findAll() {
    return tzRepo.findAll();
  }

  public Optional<TutorTermin> findById(final Long id) {
    return tzRepo.findById(id);
  }

  public void removeById(final Long id) {
    tzRepo.removeById(id);
  }

  public TutorTermin parseIntoTutorenZeit(final String tutorenName,
                                          final String slotZeit,
                                          final String slotDatum) {
    return TutorTermin.from(tutorenName, slotZeit, slotDatum);
  }

  public List<TutorTermin> parseTutorZeitenFromReq(final Map<String, String[]> paramMap) {
    final String[] tutorTermin = paramMap.get("tutorenTermine");
    List<TutorTermin> parsedList;

    if (tutorTermin == null) {
      parsedList = new ArrayList<>();
    } else {
      parsedList = Arrays.stream(tutorTermin)
              .map(TutorTermin::fromParseable)
              .collect(Collectors.toList());
    }

    return parsedList;
  }
}
