package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.FormParams;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorZeitService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TutorenController {

    private final transient TutorZeitService tzService;

    public TutorenController(final TutorZeitService tzService) {
        this.tzService = tzService;
    }

    @GetMapping("/tutorenansicht")
    public String handleTutorenAnsicht() {
        return "tutorenansicht";
    }

    @PostMapping("/tutorenansicht")
    public String handlePraUebungPost(final Model model,
                                      final FormParams params,
                                      final HttpServletRequest req) {
        final var parsedZeitslots
                = tzService.parseTutorZeitenFromReq(req);
        model.addAttribute("params", params);
        model.addAttribute("zeitslots", parsedZeitslots);

        return "tutorenansicht";
    }

}
