package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;

import java.util.Optional;

public final class StudentenControllerHelper {
  private StudentenControllerHelper() { }

  public static String selectHtmlFromConfigForModus(
      final Optional<PraktischeUbungswocheConfig> maybeUbungswocheConfig) {

    final String html;

    if (maybeUbungswocheConfig.isEmpty()) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else if (maybeUbungswocheConfig.get().getModus() == Gruppenmodus.INDIVIDUAL) {
      html = "redirect:/ansicht/individual/studenten_ansicht";
    } else {
      html = "redirect:/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }

  public static String selectHtmlFromConfig(
      final PraktischeUbungswocheConfig maybeUbungswocheConfig) {

    final String html;

    if (maybeUbungswocheConfig == null) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else {
      html = "/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }
}
