package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;

public enum Gruppenmodus {
  INDIVIDUAL(0),
  GRUPPE(1);

  @Getter
  private final int value;

  Gruppenmodus(final int val) {
    this.value = val;
  }

  @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
  public static Gruppenmodus from(final int modus) {
    Gruppenmodus gruppenmodus;
    if (modus == 1) {
      gruppenmodus = GRUPPE;
    } else {
      gruppenmodus = INDIVIDUAL;
    }
    return gruppenmodus;
  }

}
