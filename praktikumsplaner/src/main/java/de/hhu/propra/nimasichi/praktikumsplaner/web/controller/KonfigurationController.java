package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@SuppressWarnings("PMD.LawOfDemeter")
public class KonfigurationController {

  private static final transient String
      PARAMS_MODEL_NAME = "params";
  private static final transient String
      TUTOREN_TERMINE_MODEL_NAME = "tutorTermine";

  private final transient TutorTerminService tzService;

  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;
  private final transient WochenbelegungRepo wochenbelegungRepo;

  public KonfigurationController(final TutorTerminService service,
                                 final UbungswocheConfigRepo ubungswocheConfigRepo,
                                 final WochenbelegungRepo wochenbelegungRepo) {
    this.tzService = service;
    this.ubungswocheConfigRepo = ubungswocheConfigRepo;
    this.wochenbelegungRepo = wochenbelegungRepo;
  }

  @GetMapping("/konfiguration")
  public String handleConfig() {
    return "konfiguration/index";
  }

  @PostMapping("/konfiguration/tutor_termine")
  public String handleTutorenansichtPost(final Model model,
                                         final ConfigParamsForm params) {

    Optional<PraktischeUbungswocheConfig> maybeUbungswocheConfig = ubungswocheConfigRepo.findByHighestId();
    Set<TutorTermin> tutorenTermine;

    if (maybeUbungswocheConfig.isEmpty()) {
      tutorenTermine = new HashSet<>();
    } else {
      tutorenTermine = maybeUbungswocheConfig.get().getTutorTermine();
    }

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, tutorenTermine);

    return "konfiguration/tutor_termine";
  }

  @PostMapping("/tutor_termin_hinzufugen")
  public String handleAddTutor(final Model model,
                               final ConfigParamsForm params,
                               final HttpServletRequest req,
                               final String tutorName,
                               final String slotZeit,
                               final String slotDatum) {

    final var parsedTutorTermine
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    final var parsedSlot = tzService.parseIntoTutorenZeit(tutorName, slotZeit, slotDatum);
    parsedTutorTermine.add(parsedSlot);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTutorTermine);

    return "konfiguration/tutor_termine";
  }

  @PostMapping("/tutor_termin_loschen/{idx}")
  public String handleDeleteTutor(final Model model,
                                  @PathVariable("idx") final int index,
                                  final ConfigParamsForm params,
                                  final HttpServletRequest req) {

    final var parsedTutorTermine
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    parsedTutorTermine.remove(index);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTutorTermine);

    return "konfiguration/tutor_termine";
  }

  @PostMapping("/konfiguration_abschliessen")
  public String handleKonfigurationAbschliessen(final Model model,
                                                final ConfigParamsForm params,
                                                final HttpServletRequest req) {

    final var parsedTutorTermine
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTutorTermine);

    return "konfiguration/konfiguration_abschliessen";
  }

  @PostMapping("/tutorenansicht")
  public String handlePraUebungPost(final Model model,
                                    final ConfigParamsForm params,
                                    final HttpServletRequest req) {

    final var parsedTutorTermine
            = tzService.parseTutorZeitenFromReq(req.getParameterMap());

    PraktischeUbungswocheConfig config = PraktischeUbungswocheConfig
        .makeConfigAndFillZeiten(params, new HashSet<>(parsedTutorTermine));
    ubungswocheConfigRepo.save(config);

    Wochenbelegung wochenbelegung = Wochenbelegung.fromConfig(config);
    wochenbelegungRepo.save(wochenbelegung);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTutorTermine);

    System.out.println(ubungswocheConfigRepo.findAll());
    System.out.println(wochenbelegungRepo.findAll());

    return "ansicht/orga/orga_tuto_ansicht";
  }

}
