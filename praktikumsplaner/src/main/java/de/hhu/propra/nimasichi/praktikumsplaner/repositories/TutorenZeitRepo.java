package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorenZeit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TutorenZeitRepo {
    @Getter
    @Setter
    private PraktischeUbungswocheConfig currentConfig;

    public TutorenZeitRepo() {
        this.currentConfig = new PraktischeUbungswocheConfig();
    }

    public List<TutorenZeit> findAll() {
        return currentConfig.getZeitslots();
    }

    public Optional<TutorenZeit> findById(final UUID id) {
        final var elem = currentConfig.getZeitslots().stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);

        return Optional.ofNullable(elem);
    }

    public void add(final TutorenZeit tz) {
        currentConfig.getZeitslots().add(tz);
    }

    public void removeById(final UUID uuid) {
        final var optTutorenZeit = findById(uuid);

        if (optTutorenZeit.isEmpty()) {
            throw new RuntimeException("uuid = "
                    + uuid + ", optTutorenZeit war null");
        } else {
            currentConfig.getZeitslots().remove(optTutorenZeit.get());
        }
    }

    public void sortEntriesByDate() {
        currentConfig.getZeitslots()
                .sort(Comparator
                        .comparing(TutorenZeit::getZeit));
    }

}
