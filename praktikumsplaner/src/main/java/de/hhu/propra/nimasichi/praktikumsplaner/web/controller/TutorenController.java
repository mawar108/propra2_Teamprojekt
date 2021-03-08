package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

  private final transient TutorTerminService tzService;

  public TutorenController(final TutorTerminService tzService) {
    this.tzService = tzService;
  }

  @GetMapping("/tutorenansicht")
  public String handleTutorenAnsicht() {
    return "tutorenansicht";
  }

  @PostMapping("/tutorenansicht")
  public String handlePraUebungPost(final Model model,
                                    final ConfigParamsForm params,
                                    final HttpServletRequest req) {
    final var parsedZeitslots
        = tzService.parseTutorZeitenFromReq(req.getParameterMap());
    model.addAttribute("params", params);
    model.addAttribute("zeitslots", parsedZeitslots);

    return "tutorenansicht";
  }

}
