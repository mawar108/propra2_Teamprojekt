package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MODUS_INDIVIDUAL;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class AnmeldungsmodusService {

  private final transient UbungswocheConfigService ubwoService;

  public AnmeldungsmodusService(
      final UbungswocheConfigService ubwoService
  ) {
    this.ubwoService = ubwoService;
  }

  public String getRedirectForStudentAnmeldung(
      final OAuth2User principal
  ) {
    final var authorities = principal.getAuthorities();

    String html;
    if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))
        && authorities.size() == 1) {
      final var maybeConfig = ubwoService.getAktuelleUbungswocheConfig();

      if (maybeConfig.isPresent()) {
        final var config = maybeConfig.get();
        if (config.getModus() == MODUS_INDIVIDUAL) {
          html = "redirect:/ansicht/individual/anmeldung";
        } else {
          html = "redirect:/ansicht/gruppe/studenten_ansicht";
        }
      } else {
        html = "ansicht/error/keine_ubung";
      }
    } else {
      html = "index";
    }

    return html;
  }

}
