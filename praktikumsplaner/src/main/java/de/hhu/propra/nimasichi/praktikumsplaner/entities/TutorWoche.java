package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public final class TutorWoche {

    @Getter
    private final List<TutorenZeit> tutorenZeiten;

    private TutorWoche(final List<TutorenZeit> tutorenZeiten) {
        this.tutorenZeiten = tutorenZeiten;

    }

    public static TutorWoche fromConfig(final PraktischeUbungswocheConfig config) {
        return new TutorWoche(config.getZeitslots());
    }
}
