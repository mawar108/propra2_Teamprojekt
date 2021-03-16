package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.HtmlSelectorHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class StudentenController {

  private final transient ZeitslotService zsService;
  private final transient UbungswocheConfigService ucService;

  public StudentenController(
      final ZeitslotService zsService,
      final UbungswocheConfigService ucService) {
    this.zsService = zsService;
    this.ucService = ucService;
  }

  @GetMapping("/student/ansicht")
  public String handleStudentenAnsicht(final Model model) {
    final var maybeUbWocheConf
        = ucService.getAktuelleUbungswocheConfig();

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
    final var maybeUbConfig = ucService.getAktuelleUbungswocheConfig();

    String html;
    if (maybeUbConfig.isPresent()) {
      html = "/ansicht/gruppe/studenten_ansicht";
      final var freieZeitslots = zsService.getAktuelleFreieZeitslotsSorted();

      model.addAttribute("freieZeitslots", freieZeitslots);
      model.addAttribute("config", maybeUbConfig.get());
    } else {
      html = "redirect:/ansicht/error/keine_ubung";
    }

    return html;
  }
}
