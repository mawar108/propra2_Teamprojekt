package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

  @GetMapping("/tutorenansicht")
  public String handleTutorenAnsicht() {
    return "ansicht/orga/orga_tuto_ansicht";
  }

}
