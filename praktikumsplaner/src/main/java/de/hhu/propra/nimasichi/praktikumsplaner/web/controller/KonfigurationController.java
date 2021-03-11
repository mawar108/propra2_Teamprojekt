package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HttpParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.PARAMS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.TUTOREN_TERMINE_MODEL_NAME;

@Controller
@SuppressWarnings("PMD.LawOfDemeter")
public class KonfigurationController {

  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;
  private final transient WochenbelegungRepo wochenbelegungRepo;

  public KonfigurationController(final UbungswocheConfigRepo ubungswocheConfigRepo,
                                 final WochenbelegungRepo wochenbelegungRepo) {
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

    final var maybeUbWoConfig
        = ubungswocheConfigRepo.findByHighestId();
    Set<TutorTermin> tutorenTermine;

    if (maybeUbWoConfig.isEmpty()) {
      tutorenTermine = new HashSet<>();
    } else {
      tutorenTermine = maybeUbWoConfig.get().getTutorTermine();
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
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());
    final var parsedSlot
        = TutorTermin.from(tutorName, slotZeit, slotDatum);
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
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());
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
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTutorTermine);

    return "konfiguration/konfiguration_abschliessen";
  }

  @PostMapping("/tutorenansicht")
  public String handlePraUebungPost(final Model model,
                                    final ConfigParamsForm params,
                                    final HttpServletRequest req) {

    final var parsedTutorTermine
            = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());

    final var config = PraktischeUbungswocheConfig
        .makeConfigAndFillZeiten(params,
            new HashSet<>(parsedTutorTermine));

    ubungswocheConfigRepo.save(config);

    final var wochenbelegung
        = Wochenbelegung.fromConfig(config);
    wochenbelegungRepo.save(wochenbelegung);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME,
        parsedTutorTermine);

    System.out.println(ubungswocheConfigRepo.findAll());
    System.out.println(wochenbelegungRepo.findAll());

    return "ansicht/orga/orga_tuto_ansicht";
  }

}
