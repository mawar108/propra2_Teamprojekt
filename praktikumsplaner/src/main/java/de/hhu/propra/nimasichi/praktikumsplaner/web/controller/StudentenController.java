package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HtmlSelectorHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class StudentenController {

  private final transient UbungswocheConfigRepo ubWoConfRepo;
  private final transient ZeitslotService zsService;
  private final transient UbungswocheConfigService ucService;

  public StudentenController(
      final UbungswocheConfigRepo ubConfRepo,
      final ZeitslotService zsService,
      final UbungswocheConfigService ucService) {
    this.ubWoConfRepo = ubConfRepo;
    this.zsService = zsService;
    this.ucService = ucService;
  }

  @GetMapping("/student/ansicht")
  public String handleStudentenAnsicht(final Model model) {
    final var maybeUbWocheConf
        = ubWoConfRepo.findByHighestId();

    model.addAttribute("aktuelleUbung",
        maybeUbWocheConf);

    return HtmlSelectorHelper
        .selectHtmlFromConfigForModus(maybeUbWocheConf);
  }

  @GetMapping("/ansicht/error/keine_ubung")
  public String handleKeineUbung() {
    return "/ansicht/error/keine_ubung";
  }

  @GetMapping("/ansicht/gruppe/studenten_ansicht")
  public String handleStudentGruppenansicht(final Model model) {
    final var freieZeitslots = zsService.getFreieZeitslotsSorted();
    final var ubConf = ucService.getLatestUbungswocheConfig();
    final var html = HtmlSelectorHelper.selectHtmlFromConfig(ubConf);

    model.addAttribute("freieZeitslots", freieZeitslots);
    model.addAttribute("config", ubConf);

    return html;
  }
}
