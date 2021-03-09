package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@ToString
public final class TutorWochenbelegung {

  @Getter
  private final Set<TutorTermin> tutorenZeiten;

  private TutorWochenbelegung(final Set<TutorTermin> tutorenZeiten) {
    this.tutorenZeiten = tutorenZeiten;

  }

  public static TutorWochenbelegung fromConfig(final PraktischeUbungswocheConfig config) {
    return new TutorWochenbelegung(config.getTutorTermine());
  }
}
