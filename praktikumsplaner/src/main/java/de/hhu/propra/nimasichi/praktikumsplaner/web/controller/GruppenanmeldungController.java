package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.GruppenService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HttpParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.GruppenanmeldungForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.CONFIG_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.GRUPPENNAME_MODEL_NAME;

@Controller
@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.AvoidDuplicateLiterals"
})
public class GruppenanmeldungController {

  private final transient ZeitslotRepo zsRepo;
  private final transient GitHubService gitHubService;
  private final transient GruppenService grService;
  private final transient ZeitslotService zsService;
  private final transient UbungswocheConfigService ubwoService;

  public GruppenanmeldungController(
      final ZeitslotRepo zsRepo,
      final GitHubService ghService,
      final GruppenService grService,
      final ZeitslotService zsService,
      final UbungswocheConfigService ubwoService) {
    this.zsRepo = zsRepo;
    this.gitHubService = ghService;
    this.grService = grService;
    this.zsService = zsService;
    this.ubwoService = ubwoService;
  }

  @GetMapping("/ansicht/gruppe/studenten_ansicht")
  public String handleStudentGruppenansicht(final Model model) {
    final var maybeUbConfig = ubwoService.getAktuelleUbungswocheConfig();

    String html;
    if (maybeUbConfig.isPresent()) {
      html = "/ansicht/gruppe/studenten_ansicht";
      final var freieZeitslots = zsService.getAktuelleFreieZeitslotsSorted();

      model.addAttribute(ZEITSLOTS_MODEL_NAME, freieZeitslots);
      model.addAttribute(CONFIG_MODEL_NAME, maybeUbConfig.get());
    } else {
      html = "redirect:/ansicht/error/keine_ubung";
    }

    return html;
  }

  @GetMapping("/ansicht/gruppe/zeitslot_belegen/{id}")
  public String handleZeitslotBelegen(final Model model,
                                      @PathVariable("id")
                                      final long zeitslotId,
                                      @AuthenticationPrincipal
                                      final OAuth2User principal) {

    final var login = principal.getAttribute("login");
    final var zeitslot = zsRepo.findZeitslotById(zeitslotId);

    if (zeitslot.isEmpty()) {
      model.addAttribute(ALERTS_MODEL_NAME,
          "Es ist ein Fehler aufgetreten (zeitslot.isEmpty() = true!)");
    } else {
      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot.get());
    }

    model.addAttribute(MITGLIEDER_MODEL_NAME, List.of(login));

    return "ansicht/gruppe/anmeldung";
  }

  @PostMapping("/ansicht/gruppe/mitglied_hinzufugen")
  public String handleAnmeldungMitgliedHinzufugen(final String mitgliedName,
                                                  final long zeitslotId,
                                                  final Model model,
                                                  final HttpServletRequest req) {
    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());
    parsedMitglieder.add(mitgliedName);

    String html;
    if (zsService.zeitslotExists(zeitslotId)) {
      model.addAttribute(ZEITSLOT_MODEL_NAME,
          zsRepo.findById(zeitslotId).get());
      model.addAttribute(MITGLIEDER_MODEL_NAME,
          parsedMitglieder);
      html = "ansicht/gruppe/anmeldung";
    } else {
      html = "ansicht/error/kein_zeitslot";
    }

    return html;
  }

  @PostMapping("/ansicht/gruppe/mitglied_loschen/{idx}")
  public String handleAnmeldungMitgliedLoschen(final Model model,
                                               final HttpServletRequest req,
                                               @PathVariable("idx")
                                               final int index,
                                               final long zeitslotId) {

    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());

    parsedMitglieder.remove(index);

    String html;

    if (zsService.zeitslotExists(zeitslotId)) {
      model.addAttribute(ZEITSLOT_MODEL_NAME,
          zsRepo.findZeitslotById(zeitslotId).get());
      model.addAttribute(MITGLIEDER_MODEL_NAME,
          parsedMitglieder);
      html = "ansicht/gruppe/anmeldung";
    } else {
      html = "ansicht/error/kein_zeitslot";
    }

    return html;
  }

  @PostMapping("/ansicht/gruppe/anmeldung_abschliessen")
  public String handleAnmeldungAbschliessen(final Model model,
                                            final HttpServletRequest req,
                                            final String gruppenname,
                                            final long zeitslotId,
                                            @AuthenticationPrincipal
                                            final OAuth2User principal) {
    String html;
    if (zsService.zeitslotExists(zeitslotId)) {
      final var zeitslot = zsRepo.findZeitslotById(zeitslotId).get();

      final var parsedMitglieder
          = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());

      final String login = principal.getAttribute("login");

      final var form
          = new GruppenanmeldungForm(
              gitHubService,
              login,
              zeitslotId,
              zsRepo,
              ubwoService,
              parsedMitglieder,
              gruppenname,
              zeitslot.getMaxPersonen()
      );

      form.validateForm();

      final var alerts = form.getAlerts();

      model.addAttribute(ALERTS_MODEL_NAME, alerts);
      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);
      model.addAttribute(GRUPPENNAME_MODEL_NAME, gruppenname);
      model.addAttribute(MITGLIEDER_MODEL_NAME, parsedMitglieder);

      if (form.isValid()) {
        html = "ansicht/gruppe/anmeldung_abschliessen";
        grService.saveGruppeToZeitslot(
            zeitslotId,
            parsedMitglieder
        );
      } else {
        html = "ansicht/gruppe/anmeldung";
      }
    } else {
      html = "ansicht/error/kein_zeitslot";
    }

    return html;
  }
}
