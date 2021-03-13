package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.praktischeubungswocheconfig.PraktischeUbungswocheConfig;

import java.util.Optional;

@Utility
@SuppressWarnings({
    "PMD.LongVariable"
})
public final class StudentenControllerHelper {
  private StudentenControllerHelper() { }

  public static String selectHtmlFromConfigForModus(
      final Optional<PraktischeUbungswocheConfig>
          maybeUbungswocheConfig) {

    String html;

    if (maybeUbungswocheConfig.isEmpty()) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else if (maybeUbungswocheConfig.get().getModus()
        == NumericConstants.MODUS_INDIVIDUAL) {
      html = "redirect:/ansicht/individual/studenten_ansicht";
    } else {
      html = "redirect:/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }

  public static String selectHtmlFromConfig(
      final PraktischeUbungswocheConfig
          maybeUbungswocheConfig) {

    String html;

    if (maybeUbungswocheConfig == null) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else {
      html = "/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }
}
