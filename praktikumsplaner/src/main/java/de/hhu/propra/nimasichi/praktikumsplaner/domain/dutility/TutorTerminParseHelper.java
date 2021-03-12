package de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.TutorTermin;

public class TutorTerminParseHelper {

  public static String tutorTerminToParseable(
      final TutorTermin tutorTermin) {

    final var zeit = tutorTermin.getZeit();
    return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + tutorTermin.getName();

  }

}
