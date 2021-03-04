package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import java.util.UUID;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.FormParams;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorZeitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrgaController {

    private final transient TutorZeitService tzService;

    public OrgaController(final TutorZeitService service) {
        this.tzService = service;
    }

    @GetMapping("/konfiguration")
    public String handleConfig(final Model model) {
        model.addAttribute("zeitslotList", tzService.findAll());
        return "konfiguration";
    }

    @PostMapping("/tutorenansicht")
    public String handleTutorenansichtPost(final Model model,
                                           final FormParams params) {

        final var tutorenWoche = tzService.createPraktischeUebungswocheConfig(params);

        model.addAttribute(
                "tutorenWoche",
                tutorenWoche);

        return "tutorenansicht";
    }

    @PostMapping("/tutorenZeitHinzufugen")
    public String handleAddTutor(final Model model,
                                 final String tutorenName,
                                 final String slotZeit,
                                 final String slotDatum) {

        tzService.parseAndAdd(tutorenName, slotZeit, slotDatum);
        model.addAttribute("zeitslotList", tzService.findAll());

        return "konfiguration";
    }

    @PostMapping("/tutorenZeitLoschen/{id}")
    public String handleDeleteTutor(final Model model,
                                    @PathVariable("id") final UUID uuid) {

        tzService.removeById(uuid);
        model.addAttribute("zeitslotList", tzService.findAll());

        return "konfiguration";
    }

}
