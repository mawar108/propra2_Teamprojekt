package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@SuppressWarnings("PMD.LawOfDemeter")
public class KonfigurationController {

  private final transient TutorTerminService tzService;
  private static final transient String PARAMS_MO_NAME = "params";
  private static final transient String ZEITSLOTS_MO_NAME = "zeitslots";

  public KonfigurationController(final TutorTerminService service) {
    this.tzService = service;
  }

  @GetMapping("/konfiguration")
  public String handleConfig() {
    return "konfiguration_index";
  }

  @PostMapping("/konfiguration_zeitslots")
  public String handleTutorenansichtPost(final Model model,
                                         final ConfigParamsForm params) {

    model.addAttribute(PARAMS_MO_NAME, params);
    model.addAttribute(ZEITSLOTS_MO_NAME, tzService.findAll());

    return "konfiguration_zeitslots";
  }

  @PostMapping("/tutorenZeitHinzufugen")
  public String handleAddTutor(final Model model,
                               final ConfigParamsForm params,
                               final HttpServletRequest req,
                               final String tutorenName,
                               final String slotZeit,
                               final String slotDatum) {

    final var parsedZeitslots
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    final var parsedSlot = tzService.parseIntoTutorenZeit(tutorenName, slotZeit, slotDatum);
    parsedZeitslots.add(parsedSlot);

    model.addAttribute(PARAMS_MO_NAME, params);
    model.addAttribute(ZEITSLOTS_MO_NAME, parsedZeitslots);

    return "konfiguration_zeitslots";
  }

  @PostMapping("/tutorenZeitLoschen/{idx}")
  public String handleDeleteTutor(final Model model,
                                  @PathVariable("idx") final int index,
                                  final ConfigParamsForm params,
                                  final HttpServletRequest req) {

    final var parsedZeitslots
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    parsedZeitslots.remove(index);

    model.addAttribute(PARAMS_MO_NAME, params);
    model.addAttribute(ZEITSLOTS_MO_NAME, parsedZeitslots);

    return "konfiguration_zeitslots";
  }

  @PostMapping("/konfiguration_abschliessen")
  public String handleKonfigurationAbschliessen(final Model model,
                                                final ConfigParamsForm params,
                                                final HttpServletRequest req) {

    final var parsedZeitslots
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());

    model.addAttribute(PARAMS_MO_NAME, params);
    model.addAttribute(ZEITSLOTS_MO_NAME, parsedZeitslots);

    return "konfiguration_abschliessen";
  }
}
