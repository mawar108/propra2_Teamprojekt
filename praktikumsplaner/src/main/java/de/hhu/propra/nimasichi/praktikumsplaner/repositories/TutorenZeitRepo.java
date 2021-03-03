package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.controller.TutorenZeit;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TutorenZeitRepo {
    //private final List<TutorenZeit> tutorenZeitList;

    @Getter
    @Setter
    private PraktischeUbungswocheConfig currentConfig;

    public TutorenZeitRepo() {
        //this.tutorenZeitList = new ArrayList<>();
        currentConfig = new PraktischeUbungswocheConfig();
    }

    public List<TutorenZeit> findAll() {
        return currentConfig.getZeitslots();
    }

    public Optional<TutorenZeit> findByID(UUID id) {
        var elem = currentConfig.getZeitslots().stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);

        return Optional.ofNullable(elem);
    }

    public void add(TutorenZeit tz) {
        currentConfig.getZeitslots().add(tz);
    }

    public void removeById(UUID uuid) {
        var optTutorenZeit = findByID(uuid);

        if (optTutorenZeit.isEmpty()) {
            throw new RuntimeException("uuid = " + uuid + ", optTutorenZeit war null");
        } else {
            currentConfig.getZeitslots().remove(optTutorenZeit.get());
        }
    }

    public void sortByDate() {
        currentConfig.getZeitslots().sort(Comparator.comparing(TutorenZeit::getZeit));
    }

}
