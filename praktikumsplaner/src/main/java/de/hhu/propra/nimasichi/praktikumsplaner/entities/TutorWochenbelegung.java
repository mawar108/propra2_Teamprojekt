package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public final class TutorWochenbelegung {

  @Getter
  private final List<TutorTermin> tutorenZeiten;

  private TutorWochenbelegung(final List<TutorTermin> tutorenZeiten) {
    this.tutorenZeiten = tutorenZeiten;

  }

  public static TutorWochenbelegung fromConfig(final PraktischeUbungswocheConfig config) {
    return new TutorWochenbelegung(config.getZeitslots());
  }
}
