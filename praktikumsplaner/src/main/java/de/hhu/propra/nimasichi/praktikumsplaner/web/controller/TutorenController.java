package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

  private final transient TutorTerminService tzService;
  private final transient UbungswocheConfigRepo ubungswocheConfigRepo;
  private static final transient String
          PARAMS_MODEL_NAME = "params";
  private static final transient String
          TUTOREN_TERMINE_MODEL_NAME = "tutorTermine";

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
