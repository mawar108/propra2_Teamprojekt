package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.RestplatzeService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.RestplatzBelegenForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.RESTPLATZE_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;


@Controller
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class RestplatzeanmeldungController {

  private final transient ZeitslotRepo zsRepo;
  private final transient RestplatzeService restplatzeService;
  private final transient GitHubService gitHubService;
  private final transient UbungswocheConfigService ubwoService;

  public RestplatzeanmeldungController(final ZeitslotRepo zsRepo,
                                       final RestplatzeService restplatzeService,
                                       final GitHubService gitHubService,
                                       final UbungswocheConfigService ubwoService) {
    this.zsRepo = zsRepo;
    this.restplatzeService = restplatzeService;
    this.gitHubService = gitHubService;
    this.ubwoService = ubwoService;
  }

  @GetMapping("/ansicht/gruppe/restplatze/anmeldung")
  public String handleRestplatzeanmeldung(final Model model) {
    model.addAttribute(RESTPLATZE_MODEL_NAME, restplatzeService.getAktuelleRestplatze());
    return "ansicht/gruppe/restplatze/anmeldung";
  }

  @PostMapping("/ansicht/gruppe/restplatze/belegen/{id}")
  public String handleRestplatzebelegen(final Model model,
                                        @PathVariable ("id") final int zeitslotId,
                                        @AuthenticationPrincipal final OAuth2User principal) {

    String html;

    final String login = principal.getAttribute("login");

    final var rbForm = new RestplatzBelegenForm(
        gitHubService,
        login,
        zeitslotId,
        zsRepo,
        ubwoService
    );

    rbForm.validateForm();
    model.addAttribute("user", login);

    if (rbForm.isValid()) {
      final var zeitslot = rbForm.getZeitslot();

      zeitslot.addToGruppe(login);
      zsRepo.save(zeitslot);

      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);

      html = "ansicht/gruppe/restplatze/anmeldung_success";
    } else {
      model.addAttribute(ALERTS_MODEL_NAME, rbForm.getAlerts());
      model.addAttribute(RESTPLATZE_MODEL_NAME, restplatzeService.getAktuelleRestplatze());
      html = "ansicht/gruppe/restplatze/anmeldung";
    }

    return html;
  }
}
