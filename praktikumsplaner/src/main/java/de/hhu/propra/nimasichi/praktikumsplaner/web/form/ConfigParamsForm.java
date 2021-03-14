package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility.DateParseHelper;
import lombok.Value;

import java.util.Set;

@Value
@SuppressWarnings({
    "PMD.DefaultPackage",
    "PMD.CommentDefaultAccessModifier"
})
public class ConfigParamsForm {
  String name;
  int modus;
  String anStartdatum;
  String anStartzeit;
  String anSchlussdatum;
  String anSchlusszeit;
  int minPersonen;
  int maxPersonen;

  public UbungswocheConfig makeConfigAndFillZeiten(
          final Set<TutorTermin> tutorenZeiten) {

    final var praUbungswocheCfg =
            new UbungswocheConfig();

    praUbungswocheCfg.setName(name);

    praUbungswocheCfg.setAnmeldestart(
            DateParseHelper
                    .mergeDateTimeStrings(
                            anStartdatum,
                            anStartzeit)
    );

    praUbungswocheCfg.setAnmeldeschluss(
            DateParseHelper
                    .mergeDateTimeStrings(
                            anSchlussdatum,
                            anSchlusszeit)
    );

    praUbungswocheCfg
            .setModus(modus);
    praUbungswocheCfg
            .setMinPersonen(minPersonen);
    praUbungswocheCfg
            .setMaxPersonen(maxPersonen);

    praUbungswocheCfg.setTutorTermine(tutorenZeiten);
    return praUbungswocheCfg;
  }
}
