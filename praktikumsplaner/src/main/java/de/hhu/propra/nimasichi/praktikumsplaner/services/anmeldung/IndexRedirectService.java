package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MODUS_INDIVIDUAL;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_TUTOR;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_USER;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class IndexRedirectService {

  private final transient UbungswocheConfigService ubwoService;

  public IndexRedirectService(
      final UbungswocheConfigService ubwoService
  ) {
    this.ubwoService = ubwoService;
  }


  public String getRedirectForStudentAnmeldung(final OAuth2User principal) {
    String html;
    if (istStudent(principal)) {
      html = getStudentRedirect();
    } else if (istTutor(principal)) {
      html = getTutorRedirect();

    } else {
      html = "index";
    }

    return html;
  }

  private Boolean istStudent(final OAuth2User principal) {
    final var authorities = principal.getAuthorities();

    return authorities.contains(new SimpleGrantedAuthority(ROLE_USER))
            && authorities.size() == 1;
  }

  private Boolean istTutor(final OAuth2User principal) {
    final var authorities = principal.getAuthorities();

    return authorities.contains(new SimpleGrantedAuthority(ROLE_TUTOR));
  }


  private String getStudentRedirect() {
    final var maybeConfig = ubwoService.getAktuelleUbungswocheConfig();
    String html;
    if (maybeConfig.isPresent()) {
      final var config = maybeConfig.get();
      if (config.getModus() == MODUS_INDIVIDUAL) {
        html = "redirect:/ansicht/individual/anmeldung";
      } else {
        html = "redirect:/ansicht/gruppe/index";
      }
    } else {
      html = "ansicht/error/keine_ubung";
    }
    return html;
  }

  private String getTutorRedirect() {
    return "redirect:/tutorenansicht";
  }

}
