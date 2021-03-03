package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.controller.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TutorZeitService {

    private TutorenZeitRepo tzRepo;

    public TutorZeitService(TutorenZeitRepo tzRepo) {
        this.tzRepo = tzRepo;
    }

    public void parseAndAdd(String tutorenName, String slotZeit, String slotDatum) {
        var tutorenZeit = TutorenZeit.from(tutorenName, slotZeit, slotDatum);
        tzRepo.add(tutorenZeit);
    }

    public List<TutorenZeit> findAll() {
        return tzRepo.findAll();
    }

    public void insertFromRawParsable(String[] tz) {
        List<TutorenZeit> tutorenZeiten;

        if (tz != null) {
            tutorenZeiten =
                    Arrays.stream(tz)
                            .map(TutorenZeit::fromParseable)
                            .collect(Collectors.toList());
        } else {
            tutorenZeiten = new ArrayList<>();
        }

        tzRepo.add(
                new TutorenZeit(tutorenName,
                        DateService.mergeDateTimeStrings(slotDatum, slotZeit)));

        tzRepo.sortByDate();
    }
}
