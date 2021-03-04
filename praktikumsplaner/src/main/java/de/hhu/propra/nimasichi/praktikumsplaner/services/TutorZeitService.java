package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.FormParams;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorWoche;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    public void parseAndAdd(final String tutorenName,
                            final String slotZeit,
                            final String slotDatum) {
        final var tutorenZeit =
                TutorenZeit.from(tutorenName, slotZeit, slotDatum);

        tzRepo.addZeitslot(tutorenZeit);
    }

    public TutorWoche createPraktischeUebungswocheConfig(final FormParams params) {
        final var tutorenZeiten = findAll();

        final var config =
                PraktischeUbungswocheConfig
                        .makeConfigAndFillZeiten(params, tutorenZeiten);

        return TutorWoche.fromConfig(config);
    }

}
