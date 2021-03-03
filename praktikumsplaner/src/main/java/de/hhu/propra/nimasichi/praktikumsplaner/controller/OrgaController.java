package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import java.util.UUID;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorWoche;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorZeitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrgaController {

    private final TutorZeitService tzService;

    public OrgaController(final TutorZeitService service) {
        this.tzService = service;
    }

    @GetMapping("/konfiguration")
    public String handleConfig(final Model model) {
        model.addAttribute("config", tzService.getCurrentConfig());
        return "konfiguration";
    }

    @PostMapping("/tutorenansicht")
    public String handleTutorenansichtPost(final Model model,
                                           final String name,
                                           final int modus,
                                           final String anmeldestartdatum,
                                           final String anmeldestartzeit,
                                           final String anmeldeschlussdatum,
                                           final String anmeldeschlusszeit,
                                           final int minPersonen,
                                           final int maxPersonen) {

        final var tutorenZeiten = tzService.findAll();
        final var config =
                PraktischeUbungswocheConfig
                        .makeConfig(
                                name,
                                modus,
                                anmeldestartdatum,
                                anmeldestartzeit,
                                anmeldeschlussdatum,
                                anmeldeschlusszeit,
                                minPersonen,
                                maxPersonen
                        );

        config.setZeitslots(tutorenZeiten);

        tzService.saveConfig(config);

        final var tutorenWoche =
                new TutorWoche(config.getZeitslots().get(0).getName());

        tutorenWoche.addWochenZeiten(config.getZeitslots());

        model.addAttribute(
                "tutorenWoche",
                tutorenWoche.getWochenZeiten().entrySet());

        return "tutorenansicht";
    }

//    @PostMapping("/konfiguration")
//    public String handleConfigPost(final Model model,
//                                   final String name,
//                                   final int modus,
//                                   final String anmeldestartdatum,
//                                   final String anmeldestartzeit,
//                                   final String anmeldeschlussdatum,
//                                   final String anmeldeschlusszeit,
//                                   final int minPersonen,
//                                   final int maxPersonen) {
//
//        final var config = PraktischeUbungswocheConfig.makeConfig(
//                name, modus,
//                anmeldestartdatum, anmeldestartzeit,
//                anmeldeschlussdatum, anmeldeschlusszeit,
//                minPersonen, maxPersonen
//        );
//
//        model.addAttribute("config", config);
//
//        return "redirect:/tutorenansicht";
//    }

    @PostMapping("/tutorenZeitHinzufugen")
    public String handleAddTutor(final Model model,
                                 final String tutorenName,
                                 final String slotZeit,
                                 final String slotDatum) {

        tzService.parseAndAdd(tutorenName, slotZeit, slotDatum);
        model.addAttribute("config", tzService.getCurrentConfig());

        return "konfiguration";
    }

    @PostMapping("/tutorenZeitLoschen/{id}")
    public String handleDeleteTutor(final Model model,
                                    @PathVariable("id") final UUID uuid) {

        tzService.removeById(uuid);
        model.addAttribute("config", tzService.getCurrentConfig());

        return "konfiguration";
    }

}
