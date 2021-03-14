package de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;

@Utility
public final class TutorTerminParseHelper {

  private TutorTerminParseHelper() { }

  public static String tutorTerminToParseable(
      final TutorTermin tutorTermin) {
    return tutorTermin.getLocalDate()
        + ";" + tutorTermin.getLocalTime()
        + ";" + tutorTermin.getName();
  }

}
