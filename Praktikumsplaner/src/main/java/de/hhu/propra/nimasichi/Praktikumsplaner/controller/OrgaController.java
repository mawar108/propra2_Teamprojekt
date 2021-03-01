package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class OrgaController {

    @GetMapping("/konfiguration")
    public String handleConfig() {
        return "konfiguration";
    }


    @PostMapping("/konfiguration")
    public String handleConfigPost(Model model, PraktischeUbungswocheKonfigurationPostAdapter config) {
        config.create();
        return "redirect:/tutorenansicht";
    }

    @Data
    @AllArgsConstructor
    private class PraktischeUbungswocheKonfigurationPostAdapter {

        String name;
        int modus;

        LocalDate anmeldestartdatum;
        LocalTime anmeldestartzeit;

        LocalDate anmeldeschlussdatum;
        LocalTime anmeldeschlusszeit;

        int minPersonen;
        int maxPersonen;

        private PraktischeUbungswocheConfig create() {
            PraktischeUbungswocheConfig praktischeUbungswocheConfig = new PraktischeUbungswocheConfig();

            praktischeUbungswocheConfig.setName(name);
            praktischeUbungswocheConfig.setAnmeldestart(LocalDateTime.of(anmeldestartdatum, anmeldestartzeit));
            praktischeUbungswocheConfig.setAnmeldestart(LocalDateTime.of(anmeldeschlussdatum, anmeldeschlusszeit));
            praktischeUbungswocheConfig.setMinPersonen(minPersonen);
            praktischeUbungswocheConfig.setMaxPersonen(maxPersonen);
            System.out.println(praktischeUbungswocheConfig);

            return praktischeUbungswocheConfig;
        }
    }

}
