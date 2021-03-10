package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.StudentenControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class StudentenController {

  private final transient UbungswocheConfigRepo ubWoConfRepo;

  public StudentenController(
      final UbungswocheConfigRepo ubConfRepo) {
    this.ubWoConfRepo = ubConfRepo;
  }

  @GetMapping("/student/ansicht")
  public String handleStudentenAnsicht(final Model model) {
    final var maybeUbWocheConf
        = ubWoConfRepo.findByHighestId();

    model.addAttribute("aktuelleUbung",
        maybeUbWocheConf);

    return StudentenControllerHelper
        .selectHtmlFromConfig(maybeUbWocheConf);
  }

  @GetMapping("/ansicht/error/keine_ubung")
  public String handleKeineUbung() {
    return "/ansicht/error/keine_ubung";
  }

  @GetMapping("/gruppe/studenten_ansicht")
  public String handleStudentGruppenansicht(final Model model) {
    return "/ansicht/gruppe/studenten_ansicht";
  }

}
