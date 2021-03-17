package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.AnmeldungsmodusService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings({
    "PMD.AtLeastOneConstructor"
})
public class IndexController {

  private final transient AnmeldungsmodusService anmoService;

  public IndexController(final AnmeldungsmodusService anmoService) {
    this.anmoService = anmoService;
  }

  @GetMapping("/")
  public String handleGetIndex(@AuthenticationPrincipal
                                 final OAuth2User principal) {
    return anmoService.getRedirectForStudentAnmeldung(principal);
  }

}
