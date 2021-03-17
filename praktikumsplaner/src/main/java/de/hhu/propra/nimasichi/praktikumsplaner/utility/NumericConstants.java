package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

@Utility
public final class NumericConstants {

  public static final int MODUS_INDIVIDUAL = 0;
  public static final int MODUS_GRUPPE = 1;

  public static final int MIN_GRUPPEN_SIZE = 2;
  public static final int SCHEDULED_RATE = 60_000;

  private NumericConstants() { }

  public static String modusToString(final int modus) {
    String modusString;

    if (modus == MODUS_INDIVIDUAL) {
      modusString = "Individual";
    } else {
      modusString = "Gruppe";
    }

    return modusString;
  }
}
