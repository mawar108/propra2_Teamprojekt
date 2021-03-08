package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.web.form.ConfigParamsForm;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class KonfigurationController {

    private final transient TutorTerminService tzService;

    public KonfigurationController(final TutorTerminService service) {
        this.tzService = service;
    }

    @GetMapping("/konfiguration")
    public String handleConfig() {
        return "konfiguration_index";
    }

    @PostMapping("/konfiguration_zeitslots")
    public String handleTutorenansichtPost(final Model model,
                                           final ConfigParamsForm params) {

        model.addAttribute("params", params);
        model.addAttribute("zeitslots", tzService.findAll());

        return "konfiguration_zeitslots";
    }

    @PostMapping("/tutorenZeitHinzufugen")
    public String handleAddTutor(final Model model,
                                 final ConfigParamsForm params,
                                 final HttpServletRequest req,
                                 final String tutorenName,
                                 final String slotZeit,
                                 final String slotDatum) {

        final var parsedZeitslots
                = tzService.parseTutorZeitenFromReq(req);
        final var parsedSlot = tzService.parseIntoTutorenZeit(tutorenName, slotZeit, slotDatum);
        parsedZeitslots.add(parsedSlot);

        model.addAttribute("params", params);
        model.addAttribute("zeitslots", parsedZeitslots);

        return "konfiguration_zeitslots";
    }

    @PostMapping("/tutorenZeitLoschen/{idx}")
    public String handleDeleteTutor(final Model model,
                                    @PathVariable("idx") final int index,
                                    final ConfigParamsForm params,
                                    final HttpServletRequest req) {

        final var parsedZeitslots
                = tzService.parseTutorZeitenFromReq(req);
        parsedZeitslots.remove(index);

        model.addAttribute("params", params);
        model.addAttribute("zeitslots", parsedZeitslots);

        return "konfiguration_zeitslots";
    }

    @PostMapping("/konfiguration_abschliessen")
    public String handleKonfigurationAbschliessen(final Model model,
                                                  final ConfigParamsForm params,
                                                  final HttpServletRequest req) {

        final var parsedZeitslots
                = tzService.parseTutorZeitenFromReq(req);

        model.addAttribute("params", params);
        model.addAttribute("zeitslots", parsedZeitslots);

        return "konfiguration_abschliessen";
    }
}
