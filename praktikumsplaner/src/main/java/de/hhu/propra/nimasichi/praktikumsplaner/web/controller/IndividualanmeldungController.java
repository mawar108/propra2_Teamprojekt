package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.IndividualBelegenForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.CONFIG_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;

@Controller
@SuppressWarnings("PMD.LawOfDemeter")
public class IndividualanmeldungController {

  private final transient ZeitslotRepo zsRepo;
  private final transient ZeitslotService zsService;
  private final transient GitHubService gitHubService;
  private final transient UbungswocheConfigService ubwoService;

  public IndividualanmeldungController(final ZeitslotRepo zsRepo,
                                       final ZeitslotService zsService,
                                       final GitHubService gitHubService,
                                       final UbungswocheConfigService ubwoService) {
    this.zsRepo = zsRepo;
    this.zsService = zsService;
    this.gitHubService = gitHubService;
    this.ubwoService = ubwoService;
  }

  @GetMapping("/ansicht/individual/anmeldung")
  public String handleIndividualanmeldung(final Model model) {
    final var maybeConfig
        = ubwoService.getAktuelleUbungswocheConfig();

    String html;
    if (maybeConfig.isPresent()) {
      model.addAttribute(ZEITSLOTS_MODEL_NAME, zsService.getFreieIndividualZeitslots());
      model.addAttribute(CONFIG_MODEL_NAME, maybeConfig.get());
      html = "ansicht/individual/anmeldung";
    } else {
      html = "ansicht/error/keine_ubung";
    }

    return html;
  }

  @PostMapping("/ansicht/individual/zeitslot_belegen/{id}")
  public String handleIndividualbelegen(final Model model,
                                        @PathVariable("id") final int zeitslotId,
                                        @AuthenticationPrincipal final OAuth2User principal) {

    String html;

    final String login = principal.getAttribute("login");

    final var ibForm = new IndividualBelegenForm(
        gitHubService,
        login,
        zeitslotId,
        zsRepo,
        ubwoService
    );

    ibForm.validateForm();
    model.addAttribute("user", login);

    if (ibForm.isValid()) {
      final var zeitslot = ibForm.getZeitslot();

      zeitslot.addToAngemeldeteStudenten(login);
      zsRepo.save(zeitslot);

      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);

      html = "ansicht/individual/anmeldung_success";
    } else {
      model.addAttribute(ALERTS_MODEL_NAME, ibForm.getAlerts());
      final var maybeConfig
          = ubwoService.getAktuelleUbungswocheConfig();

      if (maybeConfig.isPresent()) {
        model.addAttribute(ZEITSLOTS_MODEL_NAME, zsService.getFreieIndividualZeitslots());
        model.addAttribute(CONFIG_MODEL_NAME, maybeConfig.get());
        html = "ansicht/individual/anmeldung";
      } else {
        html = "ansicht/error/keine_ubung";
      }
    }

    return html;
  }
}
