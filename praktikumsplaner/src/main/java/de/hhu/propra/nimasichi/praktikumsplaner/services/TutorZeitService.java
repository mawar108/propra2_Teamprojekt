package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.FormParams;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorWoche;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Service
public final class TutorZeitService {

    private final transient TutorenZeitRepo tzRepo;

    public TutorZeitService(final TutorenZeitRepo repo) {
        this.tzRepo = repo;
    }

    public List<TutorenZeit> findAll() {
        return tzRepo.findAll();
    }

    public Optional<TutorenZeit> findById(final UUID uuid) {
        return tzRepo.findById(uuid);
    }

    public void removeById(final UUID uuid) {
        tzRepo.removeById(uuid);
    }

//    public void parseAndAdd(final String tutorenName,
//                            final String slotZeit,
//                            final String slotDatum) {
//        final var tutorenZeit =
//                TutorenZeit.from(tutorenName, slotZeit, slotDatum);
//
//        tzRepo.addZeitslot(tutorenZeit);
//    }

    public TutorenZeit parseIntoTutorenZeit(final String tutorenName,
                                            final String slotZeit,
                                            final String slotDatum) {
        return TutorenZeit.from(tutorenName, slotZeit, slotDatum);
    }

    public TutorWoche createPraktischeUebungswocheConfig(final FormParams params) {
        final var tutorenZeiten = findAll();

        final var config =
                PraktischeUbungswocheConfig
                        .makeConfigAndFillZeiten(params, tutorenZeiten);

        return TutorWoche.fromConfig(config);
    }

    public List<TutorenZeit> parseTutorZeitenFromReq(final HttpServletRequest req) {
        final var paramMap = req.getParameterMap();
        final var zeitslots = paramMap.get("zeitslots");

        List<TutorenZeit> parsedList;

        if (zeitslots == null) {
            parsedList = new ArrayList<>();
        } else {
            parsedList = Arrays.stream(zeitslots)
                    .map(TutorenZeit::fromParseable)
                    .collect(Collectors.toList());
        }

        return parsedList;
    }
}
