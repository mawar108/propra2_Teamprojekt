package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

  private final transient TutorTerminService tzService;
  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;

  public TutorenController(final TutorTerminService service,
                                 final UbungswocheConfigRepo ubungswocheConfigRepo) {
    this.tzService = service;
    this.ubungswocheConfigRepo = ubungswocheConfigRepo;
  }

  @GetMapping("/tutorenansicht")
  public String handleTutorenAnsicht() {
    return "ansicht/orga/orga_tuto_ansicht";
  }

}
