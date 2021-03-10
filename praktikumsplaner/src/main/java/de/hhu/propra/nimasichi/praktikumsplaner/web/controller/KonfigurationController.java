package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@SuppressWarnings("PMD.LawOfDemeter")
public class KonfigurationController {

  private final transient TutorTerminService tzService;
  private static final transient String
      PARAMS_MODEL_NAME = "params";
  private static final transient String
      TUTOREN_TERMINE_MODEL_NAME = "tutorenTermine";
  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;

  public KonfigurationController(final TutorTerminService service,
                                 final UbungswocheConfigRepo ubungswocheConfigRepo) {
    this.tzService = service;
    this.ubungswocheConfigRepo = ubungswocheConfigRepo;
  }

  @GetMapping("/konfiguration")
  public String handleConfig() {
    return "konfiguration";
  }

  @PostMapping("/konfiguration_zeitslots")
  public String handleTutorenansichtPost(final Model model,
                                         final ConfigParamsForm params) {

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, new ArrayList<>());

    return "konfiguration/konfiguration_zeitslots";
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

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedZeitslots);

    return "konfiguration/konfiguration_zeitslots";
  }

  @PostMapping("/tutorenZeitLoschen/{idx}")
  public String handleDeleteTutor(final Model model,
                                  @PathVariable("idx") final int index,
                                  final ConfigParamsForm params,
                                  final HttpServletRequest req) {

    final var parsedZeitslots
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    parsedZeitslots.remove(index);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedZeitslots);

    return "konfiguration/konfiguration_zeitslots";
  }


}
