package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorenZeit;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Comparator;
import java.util.stream.Collectors;

@Repository
public class TutorenZeitRepo {
    private final transient List<TutorenZeit> tutorenZeiten;

    public TutorenZeitRepo() {
        this.tutorenZeiten = new ArrayList<>();
    }

    public List<TutorenZeit> findAll() {
        return tutorenZeiten;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public Optional<TutorenZeit> findById(final UUID uuid) {
        final var elem = tutorenZeiten.stream()
                .filter(x -> x.getUuid().equals(uuid))
                .collect(Collectors.toList())
                .get(0);

        return Optional.ofNullable(elem);
    }

    public void add(final TutorenZeit tutorenZeit) {
        tutorenZeiten.add(tutorenZeit);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public void removeById(final UUID uuid) {
        final var optTutorenZeit = findById(uuid);

        if (!optTutorenZeit.isEmpty()) {
            tutorenZeiten.remove(optTutorenZeit.get());
        }
    }

    public void sortEntriesByDate() {
        tutorenZeiten.sort(Comparator
                .comparing(TutorenZeit::getZeit));
    }

    public void addZeitslot(final TutorenZeit zeitslot) {
        tutorenZeiten.add(zeitslot);
    }

}
