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
  private final transient UbungswocheConfigService ubwoService;

  public StudentenController(
      final ZeitslotService zsService,
      final UbungswocheConfigService ucService) {
    this.zsService = zsService;
    this.ubwoService = ucService;
  }

  @GetMapping("/student/ansicht")
  public String handleStudentenAnsicht(final Model model) {
    final var maybeUbWocheConf
        = ubwoService.getAktuelleUbungswocheConfig();

    model.addAttribute("aktuelleUbung",
        maybeUbWocheConf);

    return HtmlSelectorHelper
        .selectHtmlFromConfigForModus(maybeUbWocheConf);
  }

  @GetMapping("/ansicht/error/keine_ubung")
  public String handleKeineUbung() {
    return "/ansicht/error/keine_ubung";
  }

}
