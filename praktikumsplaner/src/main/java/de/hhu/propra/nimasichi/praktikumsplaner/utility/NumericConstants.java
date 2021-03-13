package de.hhu.propra.nimasichi.praktikumsplaner.utility;

public class NumericConstants {

  public static final int MODUS_INDIVIDUAL = 0;
  public static final int MODUS_GRUPPE = 1;

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
