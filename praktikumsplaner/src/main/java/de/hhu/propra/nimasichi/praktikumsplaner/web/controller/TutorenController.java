package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

  private final transient UbungswocheConfigRepo ubWoConfigRepo;

  public TutorenController(final UbungswocheConfigRepo ubConfigRepo) {
    this.ubWoConfigRepo = ubConfigRepo;
  }

  @GetMapping("/tutorenansicht")
  public String handleTutorenAnsicht() {
    return "ansicht/orga/orga_tuto_ansicht";
  }

}
