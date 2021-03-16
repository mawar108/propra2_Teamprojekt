package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.LetzteTutorTermineService;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HttpParseHelper;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.PARAMS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.TUTOREN_TERMINE_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_ORGA;

@Controller
@Secured(ROLE_ORGA)
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class KonfigurationController {


  private final transient UbungswocheConfigRepo ubConfRepo;
  private final transient ZeitslotRepo zsRepo;
  private final transient LetzteTutorTermineService ttService;

  public KonfigurationController(final UbungswocheConfigRepo ubConfRepo,
                                 final ZeitslotRepo zsRepo,
                                 final LetzteTutorTermineService ttService) {
    this.ubConfRepo = ubConfRepo;
    this.zsRepo = zsRepo;
    this.ttService = ttService;
  }

  @GetMapping("/konfiguration")
  public String handleConfig() {
    return "konfiguration/index";
  }

  @PostMapping("/konfiguration/tutor_termine")
  public String handleTutorenansichtPost(final Model model,
                                         final ConfigParamsForm params) {

    final var tutorenTermine = ttService.getNeueTutorTermine(
        params.getAnSchlussdatum(),
        params.getAnSchlusszeit()
    );

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

    final var parsedTermine
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());
    final var parsedSlot
        = UbungswocheConfig.tutorTerminFrom(tutorName, slotZeit, slotDatum);
    parsedTermine.add(parsedSlot);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTermine);

    return "konfiguration/tutor_termine";
  }

  @PostMapping("/tutor_termin_loschen/{idx}")
  public String handleDeleteTutor(final Model model,
                                  @PathVariable("idx") final int index,
                                  final ConfigParamsForm params,
                                  final HttpServletRequest req) {

    final var parsedTermine
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());
    parsedTermine.remove(index);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTermine);

    return "konfiguration/tutor_termine";
  }

  @PostMapping("/konfiguration_abschliessen")
  public String handleKonfigurationAbschliessen(final Model model,
                                                final ConfigParamsForm params,
                                                final HttpServletRequest req) {

    final var parsedTermine
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME, parsedTermine);

    return "konfiguration/konfiguration_abschliessen";
  }

  @PostMapping("/tutorenansicht")
  public String handlePraUebungPost(final Model model,
                                    final ConfigParamsForm params,
                                    final HttpServletRequest req) {

    final var parsedTermine
        = HttpParseHelper.parseTutorZeitenFromReq(req.getParameterMap());
    final var config = params
        .makeConfigAndFillZeiten(new HashSet<>(parsedTermine));
    ubConfRepo.save(config);

    final var zeitslots
        = config.parseTutorTerminToZeitslots();
    zsRepo.saveAll(zeitslots);

    model.addAttribute(PARAMS_MODEL_NAME, params);
    model.addAttribute(TUTOREN_TERMINE_MODEL_NAME,
        parsedTermine);

    return "ansicht/orga/orga_tuto_ansicht";
  }


}
