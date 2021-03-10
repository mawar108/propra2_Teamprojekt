package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;

import java.util.Optional;

public final class StudentenControllerHelper {
  private StudentenControllerHelper() { }

  public static String selectHtmlFromConfig(
      final Optional<PraktischeUbungswocheConfig> maybeUbungswocheConfig) {

    String html;

    if (maybeUbungswocheConfig.isEmpty()) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else if (maybeUbungswocheConfig.get().getModus() == Gruppenmodus.INDIVIDUAL) {
      html = "redirect:/ansicht/individual/studenten_ansicht";
    } else {
      html = "redirect:/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }
}
