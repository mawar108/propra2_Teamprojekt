package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TutorZeitService {

    private final TutorenZeitRepo tzRepo;

    public TutorZeitService(final TutorenZeitRepo repo) {
        this.tzRepo = repo;
    }

    public List<TutorenZeit> findAll() {
        return tzRepo.findAll();
    }

    public Optional<TutorenZeit> findById(final UUID id) {
        return tzRepo.findById(id);
    }

    public void removeById(final UUID id) {
        tzRepo.removeById(id);
    }

    public PraktischeUbungswocheConfig getCurrentConfig() {
        return tzRepo.getCurrentConfig();
    }

    public void saveConfig(final PraktischeUbungswocheConfig config) {
        tzRepo.setCurrentConfig(config);
    }

    public void parseAndAdd(final String tutorenName,
                            final String slotZeit,
                            final String slotDatum) {
        final var tutorenZeit =
                TutorenZeit.from(tutorenName, slotZeit, slotDatum);
        tzRepo.getCurrentConfig().getZeitslots().add(tutorenZeit);
    }
}
