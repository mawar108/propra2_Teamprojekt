package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.controller.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TutorZeitService {

    private final TutorenZeitRepo tzRepo;

    public TutorZeitService(TutorenZeitRepo tzRepo) {
        this.tzRepo = tzRepo;
    }

    public List<TutorenZeit> findAll() {
        return tzRepo.findAll();
    }

    public Optional<TutorenZeit> findById(UUID id) {
        return tzRepo.findByID(id);
    }

    public void removeById(UUID id) {
        tzRepo.removeById(id);
    }

    public PraktischeUbungswocheConfig getCurrentConfig() {
        return tzRepo.getCurrentConfig();
    }

    public void saveConfig(PraktischeUbungswocheConfig config) {
        tzRepo.setCurrentConfig(config);
    }

    public void parseAndAdd(final String tutorenName, final String slotZeit, final String slotDatum) {
        var tutorenZeit = TutorenZeit.from(tutorenName, slotZeit, slotDatum);
        //tzRepo.add(tutorenZeit);
        tzRepo.getCurrentConfig().getZeitslots().add(tutorenZeit);
    }
//
//    public void tryRemoveOrThrow(UUID uuid) {
//        tzRepo.removeById(uuid);
//    }
}
