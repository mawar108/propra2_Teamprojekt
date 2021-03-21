package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.orga.GruppenAndernService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_TUTOR;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_ORGA;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOTS_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.CONFIG_MODEL_NAME;

@Controller
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.ShortVariable", "PMD.LawOfDemeter"})
public class OrgaController {

  private final transient UbungswocheConfigRepo ubwoRepo;
  private final transient ZeitslotRepo zsRepo;
  private final transient GruppenAndernService grAnServices;

  public OrgaController(final UbungswocheConfigRepo ubwoRepo,
                        final ZeitslotRepo zsRepo,
                        final GruppenAndernService grAnServices) {
    this.ubwoRepo = ubwoRepo;
    this.zsRepo = zsRepo;
    this.grAnServices = grAnServices;
  }

  @GetMapping("/tutorenansicht")
  @Secured({ROLE_TUTOR, ROLE_ORGA})
  public String handleTutorenAnsicht(final Model model) {
    final List<UbungswocheConfig> configs = ubwoRepo.findAll();

    model.addAttribute("configs", configs);

    return "ansicht/orga/orga_tuto_ansicht";
  }


  @GetMapping("praktische_ubung/{id}")
  @Secured({ROLE_TUTOR, ROLE_ORGA})
  public String handlePraktischeUbungAnsicht(final Model model,
                                             @PathVariable("id") final long id,
                                             @AuthenticationPrincipal
                                               final OAuth2User principal) {

    final boolean isOrga = principal.getAuthorities()
            .contains(new SimpleGrantedAuthority(ROLE_ORGA));
    model.addAttribute("is_orga", isOrga);
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

  @PostMapping("/orga/gruppe/andern/mitglied_hinzufugen")
  @Secured(ROLE_ORGA)
  public String handleMitgliedHinzufugen(final Model model,
                                         final String gruppenName,
                                         final long zeitslotId,
                                         final long configId,
                                         final String mitgliedName) {

    model.addAttribute("is_orga", true);
    grAnServices.studentZuGruppeHinzufugen(zeitslotId, gruppenName, mitgliedName);

    final var maybeConfig = ubwoRepo.findById(configId);
    final UbungswocheConfig config = maybeConfig.get();
    final List<Zeitslot> zeitslots =
            zsRepo.findZeitslotsByUbungswocheConfigId(config.getId());

    model.addAttribute(ZEITSLOTS_MODEL_NAME, zeitslots);
    model.addAttribute(CONFIG_MODEL_NAME, config);

    return "ansicht/orga/praktische_ubung_ansicht";
  }

  @PostMapping("/orga/gruppe/andern/mitglied_loschen")
  @Secured(ROLE_ORGA)
  public String handleMitgliedLoschen(final Model model,
                                         final String gruppenName,
                                         final long zeitslotId,
                                         final long configId,
                                         final String mitgliedName) {

    model.addAttribute("is_orga", true);
    grAnServices.studentAusGruppeLoschen(zeitslotId, gruppenName, mitgliedName);

    final var maybeConfig = ubwoRepo.findById(configId);
    final UbungswocheConfig config = maybeConfig.get();
    final List<Zeitslot> zeitslots =
            zsRepo.findZeitslotsByUbungswocheConfigId(config.getId());

    model.addAttribute(ZEITSLOTS_MODEL_NAME, zeitslots);
    model.addAttribute(CONFIG_MODEL_NAME, config);

    return "ansicht/orga/praktische_ubung_ansicht";
  }







}
