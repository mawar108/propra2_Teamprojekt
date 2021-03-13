package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.GruppenanmeldungAlertHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HttpParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ContainsMitglieder;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.GitHubHandle;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.MitgliederCount;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ZeitslotId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ALERTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.GRUPPENNAME_MODEL_NAME;

@Controller
@Validated
public class GruppenanmeldungController {

  private final transient WochenbelegungRepo wobeRepo;
  private final transient GitHubService gitHubService;

  public GruppenanmeldungController(
      final WochenbelegungRepo wobeRepo,
      final GitHubService ghService) {
    this.wobeRepo = wobeRepo;
    this.gitHubService = ghService;
  }

//  OLD
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
  public String handleAnmeldungMitgliedHinzufugen(final Model model,
                                                  final HttpServletRequest req,
                                                  final String mitgliedName,
                                                  final int zeitslotId) {

    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());
    final var maybeZeitslot = wobeRepo.findZeitslotById(zeitslotId);
    final var alerts = new ArrayList<String>();
    final var zeitslot = GruppenanmeldungAlertHelper
        .getZeitslotOrAddAlert(maybeZeitslot, alerts);

    if (!gitHubService.doesUserExist(mitgliedName)) {
      alerts.add("Github-Name nicht vorhanden!");
    }

    if (zeitslot != null) {
      if (parsedMitglieder.size() < zeitslot.getMaxPersonen()) {
        parsedMitglieder.add(mitgliedName);
      } else {
        alerts.add("Die maximale Mitgliederanzahl von "
            + zeitslot.getMaxPersonen()
            + " darf nicht überschritten werden.");
      }
    }

    model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);
    model.addAttribute(MITGLIEDER_MODEL_NAME, parsedMitglieder);

    return "ansicht/gruppe/anmeldung";
  }

  @PostMapping("/ansicht/gruppe/mitglied_loschen/{idx}")
  public String handleAnmeldungMitgliedLoschen(final Model model,
                                               final HttpServletRequest req,
                                               @PathVariable("idx")
                                               final int index,
                                               final int zeitslotId) {

    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());
    final var zeitslot = wobeRepo.findZeitslotById(zeitslotId).get();

    parsedMitglieder.remove(index);

    model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot);
    model.addAttribute(MITGLIEDER_MODEL_NAME, parsedMitglieder);

    return "ansicht/gruppe/anmeldung";
  }

  @PostMapping("/ansicht/gruppe/anmeldung_abschliessen")
  public String handleAnmeldungAbschliessen(final Model model,
                                            final HttpServletRequest req,
                                            final String gruppenname,
                                            final int zeitslotId) {

    final var parsedMitglieder
        = HttpParseHelper.parseMitgliederFromReq(req.getParameterMap());
    final var zeitslot = wobeRepo.findZeitslotById(zeitslotId);

    if (zeitslot.isEmpty()) {
      model.addAttribute(ALERTS_MODEL_NAME,
          "Es ist ein Fehler aufgetreten (zeitslot.isEmpty() = true!)");
    } else {
      model.addAttribute(ZEITSLOT_MODEL_NAME, zeitslot.get());
    }

    model.addAttribute(GRUPPENNAME_MODEL_NAME, gruppenname);
    model.addAttribute(MITGLIEDER_MODEL_NAME, parsedMitglieder);

    return "ansicht/gruppe/anmeldung_abschliessen";
  }
}
