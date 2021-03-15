package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.GruppenanmeldungService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HttpParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.MitgliedHinzufugenForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.GRUPPENNAME_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;

@Controller
@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.AvoidDuplicateLiterals"
})
public class GruppenanmeldungController {

  private final transient WochenbelegungRepo wobeRepo;
  private final transient GitHubService gitHubService;
  private final transient GruppenanmeldungService gaService;
  private final transient ZeitslotService zsService;

  public GruppenanmeldungController(
      final WochenbelegungRepo wobeRepo,
      final GitHubService ghService,
      final GruppenanmeldungService gaService,
      final ZeitslotService zsService) {
    this.wobeRepo = wobeRepo;
    this.gitHubService = ghService;
    this.gaService = gaService;
    this.zsService = zsService;
  }

  @GetMapping("/ansicht/gruppe/zeitslot_belegen/{id}")
  public String handleZeitslotBelegen(final Model model,
                                      @PathVariable("id")
                                      final int zeitslotId,
                                      @AuthenticationPrincipal
                                        final OAuth2User principal) {

    final var login = principal.getAttribute("login");
    final var zeitslot = wobeRepo.findZeitslotById(zeitslotId);

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
                                                  final int zeitslotId,
                                                  final Model model,
                                                  final HttpServletRequest req) {
    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());
    parsedMitglieder.add(mitgliedName);

    String html;
    if (zsService.zeitslotExists(zeitslotId)) {
      model.addAttribute(ZEITSLOT_MODEL_NAME,
          wobeRepo.findZeitslotById(zeitslotId).get());
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
                                               final int zeitslotId) {

    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());

    parsedMitglieder.remove(index);

    String html;

    if (zsService.zeitslotExists(zeitslotId)) {
      model.addAttribute(ZEITSLOT_MODEL_NAME,
          wobeRepo.findZeitslotById(zeitslotId).get());
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
                                            final int zeitslotId) {
    String html;
    if (zsService.zeitslotExists(zeitslotId)) {
      final var zeitslot = wobeRepo.findZeitslotById(zeitslotId).get();

      final var parsedMitglieder
          = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());

      final var form
          = new MitgliedHinzufugenForm(
              zeitslotId,
              parsedMitglieder,
              gruppenname,
              wobeRepo
      );

      form.validateForm(gitHubService, zeitslot.getMaxPersonen());

      final var alerts = form.getAlerts();

      model.addAttribute(ALERTS_MODEL_NAME, alerts);
      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);
      model.addAttribute(GRUPPENNAME_MODEL_NAME, gruppenname);
      model.addAttribute(MITGLIEDER_MODEL_NAME, parsedMitglieder);

      if (form.isValid()) {
        html = "ansicht/gruppe/anmeldung_abschliessen";
        gaService.saveGruppeToZeitslot(
            (long) zeitslotId,
            new HashSet<>(parsedMitglieder));
      } else {
        html = "ansicht/gruppe/anmeldung";
      }
    } else {
      html = "ansicht/error/kein_zeitslot";
    }

    return html;
  }
}
