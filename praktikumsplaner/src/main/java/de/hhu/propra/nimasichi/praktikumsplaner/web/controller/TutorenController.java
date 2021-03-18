package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_TUTOR;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_ORGA;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.CONFIG_MODEL_NAME;

@Controller
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.ShortVariable", "PMD.LawOfDemeter"})
@Secured({ROLE_TUTOR, ROLE_ORGA})
public class TutorenController {

  private final transient UbungswocheConfigRepo ubwoRepo;
  private final transient ZeitslotRepo zsRepo;

  public TutorenController(final UbungswocheConfigRepo ubwoRepo, final ZeitslotRepo zsRepo) {
    this.ubwoRepo = ubwoRepo;
    this.zsRepo = zsRepo;
  }

  @GetMapping("/tutorenansicht")
  public String handleTutorenAnsicht(final Model model) {
    final List<UbungswocheConfig> configs = ubwoRepo.findAll();

    model.addAttribute("configs", configs);

    return "ansicht/orga/orga_tuto_ansicht";
  }


  @GetMapping("praktische_ubung/{id}")
  public String handlePraktischeUbungAnsicht(final Model model,
                                             @PathVariable("id") final long id) {
    final var maybeConfig = ubwoRepo.findById(id);

    String html;
    if (maybeConfig.isPresent()) {
      final UbungswocheConfig config = maybeConfig.get();

      final List<Zeitslot> zeitslots =
          zsRepo.findZeitslotsByUbungswocheConfigId(config.getId());

      model.addAttribute(ZEITSLOTS_MODEL_NAME, zeitslots);
      model.addAttribute(CONFIG_MODEL_NAME, config);
      html = "ansicht/orga/praktische_ubung_ansicht";
    } else {
      html = "redirect:/tutorenansicht";
    }
    return html;
  }



}
