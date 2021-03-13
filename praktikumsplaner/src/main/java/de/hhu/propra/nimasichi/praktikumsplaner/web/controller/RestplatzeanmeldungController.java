package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.RestplatzeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.RESTPLATZE_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;

@Controller
public class RestplatzeanmeldungController {

  private final transient RestplatzeService restplatzeService;
  private final transient WochenbelegungRepo wobeRepo;

  public RestplatzeanmeldungController(final RestplatzeService restplatzeService,
                                       final WochenbelegungRepo wobeRepo) {
    this.restplatzeService = restplatzeService;
    this.wobeRepo = wobeRepo;
  }

  @GetMapping("/ansicht/gruppe/restplatze/anmeldung")
  public String handleRestplatzeanmeldung(final Model model) {
    model.addAttribute(RESTPLATZE_MODEL_NAME, restplatzeService.getRestplatze());
    return "ansicht/gruppe/restplatze/anmeldung";
  }

  @PostMapping("/ansicht/gruppe/restplatze/belegen/{id}")
  public String handleRestplatzebelegen(final Model model,
                                        @PathVariable ("id") final int zeitslotId,
                                        @AuthenticationPrincipal final OAuth2User principal) {

    final String login = principal.getAttribute("login");
    final var maybeZeitslot = wobeRepo.findZeitslotById(zeitslotId);
    Zeitslot zeitslot;

    model.addAttribute("user", login);

    if (maybeZeitslot.isEmpty()) {
      model.addAttribute(ALERTS_MODEL_NAME,
              "Es ist ein Fehler aufgetreten (maybeZeitslot.isEmpty() = true!)");
    } else {
      zeitslot = maybeZeitslot.get();
      if (Wochenbelegung.zeitslotHatRestplatze(zeitslot)) {
        Wochenbelegung.addToZeitslot(zeitslot, login);
      }
    }

    return "ansicht/gruppe/restplatze/anmeldung";
  }
}
