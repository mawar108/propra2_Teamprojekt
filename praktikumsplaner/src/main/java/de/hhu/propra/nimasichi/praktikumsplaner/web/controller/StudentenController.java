package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.StudentenControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class StudentenController {

  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;

  public StudentenController(UbungswocheConfigRepo ubungswocheConfigRepo) {
    this.ubungswocheConfigRepo = ubungswocheConfigRepo;
  }

  @GetMapping("/student/ansicht")
  public String handleStudentenAnsicht(Model model) {
    Optional<PraktischeUbungswocheConfig> maybeUbungswocheConfig = ubungswocheConfigRepo.findByHighestId();

    model.addAttribute("aktuelleUbung", maybeUbungswocheConfig);
    return StudentenControllerHelper.selectHtmlFromConfig(maybeUbungswocheConfig);
  }

  @GetMapping("/ansicht/error/keine_ubung")
  public String handleKeineUbung() {
    return "/ansicht/error/keine_ubung";
  }

  @GetMapping("/gruppe/studenten_ansicht")
  public String handleStudentGruppenansicht(Model model) {

    return "/gruppe/studenten_ansicht";
  }

}
