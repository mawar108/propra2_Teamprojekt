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

  public Optional<TutorTermin> findById(final UUID uuid) {
    return tzRepo.findById(uuid);
  }

  public void removeById(final UUID uuid) {
    tzRepo.removeById(uuid);
  }

  public TutorTermin parseIntoTutorenZeit(final String tutorenName,
                                          final String slotZeit,
                                          final String slotDatum) {
    return TutorTermin.from(tutorenName, slotZeit, slotDatum);
  }

  public TutorWochenbelegung createPraktischeUebungswocheConfig(final ConfigParamsForm params) {
    final var tutorenZeiten = findAll();

    final var config =
            PraktischeUbungswocheConfig
                    .makeConfigAndFillZeiten(params, tutorenZeiten);

    return TutorWochenbelegung.fromConfig(config);
  }

  public List<TutorTermin> parseTutorZeitenFromReq(final Map<String, String[]> paramMap) {
    final String[] tutorTermin = paramMap.get("zeitslots");
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
