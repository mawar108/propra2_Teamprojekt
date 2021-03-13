package de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin;

@Utility
public final class TutorTerminParseHelper {

  private TutorTerminParseHelper() { }

  public static String tutorTerminToParseable(
      final TutorTermin tutorTermin) {

    final var zeit = tutorTermin.getZeit();
    return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + tutorTermin.getName();
  }

}
