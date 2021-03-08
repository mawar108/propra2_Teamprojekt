package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Comparator;
import java.util.stream.Collectors;

@Repository
public class TutorTerminRepo {
    private final transient List<TutorTermin> tutorenZeiten;

    public TutorTerminRepo() {
        this.tutorenZeiten = new ArrayList<>();
    }

    public List<TutorTermin> findAll() {
        return tutorenZeiten;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public Optional<TutorTermin> findById(final UUID uuid) {
        final var elem = tutorenZeiten.stream()
                .filter(x -> x.getUuid().equals(uuid))
                .collect(Collectors.toList())
                .get(0);

        return Optional.ofNullable(elem);
    }

    public void add(final TutorTermin tutorTermin) {
        tutorenZeiten.add(tutorTermin);
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
                .comparing(TutorTermin::getZeit));
    }

    public void addZeitslot(final TutorTermin zeitslot) {
        tutorenZeiten.add(zeitslot);
    }

}
