package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@SuppressWarnings("PMD.LawOfDemeter")
public class TutorTerminService {

  public TutorTermin parseIntoTutorenZeit(final String tutorenName,
                                          final String slotZeit,
                                          final String slotDatum) {
    return TutorTermin.from(tutorenName, slotZeit, slotDatum);
  }

  public List<TutorTermin> parseTutorZeitenFromReq(
      final Map<String, String[]> paramMap) {

    final String[] tutorTermin = paramMap.get("tutorTermine");
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
