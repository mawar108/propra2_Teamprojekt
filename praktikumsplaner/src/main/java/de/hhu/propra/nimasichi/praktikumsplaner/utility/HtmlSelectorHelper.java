package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;

import java.util.Optional;

@Utility
@SuppressWarnings({
    "PMD.LongVariable",
    "PMD.LawOfDemeter"
})
public final class HtmlSelectorHelper {
  private HtmlSelectorHelper() { }

  public static String selectHtmlFromConfigForModus(
      final Optional<UbungswocheConfig>
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
      final UbungswocheConfig
          maybeUbungswocheConfig) {

    String html;

    if (maybeUbungswocheConfig == null) {
      html = "redirect:/ansicht/error/keine_ubung";
    } else {
      html = "/ansicht/gruppe/studenten_ansicht";
    }

    return html;
  }

  public static String selectHtmlForFormValidation(final boolean isValid) {
    String html;
    if (isValid) {
      html = "ansicht/gruppe/anmeldung_abschliessen";
    } else {
      html = "ansicht/gruppe/anmeldung";
    }
    return html;
  }
}
