package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
public class OrgaController {

    @GetMapping("/konfiguration")
    public String handleConfig(Model model) {
        List<TutorenZeit> zeiten = List.of(
                new TutorenZeit("Hans", LocalDateTime.of(2000, 12, 1, 5, 2)),
                new TutorenZeit("Peter", LocalDateTime.of(2000, 12, 1, 6, 2)),
                new TutorenZeit("Ulli", LocalDateTime.of(2000, 12, 1, 6, 2))
        );
        model.addAttribute("tutorenZeiten", zeiten);
        return "konfiguration";
    }


    @PostMapping("/konfiguration")
    public String handleConfigPost(Model model,
                                   PraktischeUbungswocheKonfigurationPostAdapter config) {
        System.out.println("Bin im controller");
        config.create();
        return "redirect:/tutorenansicht";
    }

    @Data
    @AllArgsConstructor
    private class PraktischeUbungswocheKonfigurationPostAdapter {

        String name;
        int modus;

        String anmeldestartdatum;
        String anmeldestartzeit;

        String anmeldeschlussdatum;
        String anmeldeschlusszeit;

        int minPersonen;
        int maxPersonen;

        private LocalDate stringToLocalDate(String datum) {
            System.out.println(datum);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.GERMANY);

            System.out.println("Parse to Local Date: " + LocalDate.parse(datum, formatter));

            return LocalDate.parse(datum, formatter);
        }

        private LocalTime stringToLocalTime(String zeit) {
            System.out.println(zeit);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            formatter = formatter.withLocale(Locale.GERMANY);

            LocalTime time = LocalTime.parse(zeit, formatter);
            System.out.println("Parse to Local Time: " + time);

            return time;
        }

        private PraktischeUbungswocheConfig create() {
            var praktischeUbungswocheConfig = new PraktischeUbungswocheConfig();

            praktischeUbungswocheConfig.setName(name);
            praktischeUbungswocheConfig.setAnmeldestart(
                    LocalDateTime.of(stringToLocalDate(anmeldestartdatum),
                            stringToLocalTime(anmeldestartzeit)));
            praktischeUbungswocheConfig.setAnmeldeschluss(
                    LocalDateTime.of(stringToLocalDate(anmeldeschlussdatum),
                            stringToLocalTime(anmeldeschlusszeit)));
            praktischeUbungswocheConfig.setModus(Gruppenmodus.from(modus));
            praktischeUbungswocheConfig.setMinPersonen(minPersonen);
            praktischeUbungswocheConfig.setMaxPersonen(maxPersonen);
            System.out.println(praktischeUbungswocheConfig);

            return praktischeUbungswocheConfig;
        }
    }

}
