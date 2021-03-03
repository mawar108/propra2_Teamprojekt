package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Gruppenmodus;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.TutorenZeitRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorZeitService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static de.hhu.propra.nimasichi.praktikumsplaner.services.DateService.stringToLocalDate;
import static de.hhu.propra.nimasichi.praktikumsplaner.services.DateService.stringToLocalTime;

@Controller
public class OrgaController {

    private TutorZeitService tzService;

    public OrgaController(TutorZeitService tzService) {
        this.tzService = tzService;
    }

    @GetMapping("/konfiguration")
    public String handleConfig(Model model) {
//        List<TutorenZeit> zeiten = List.of(
//                new TutorenZeit("Hans", LocalDateTime.of(2000, 12, 1, 5, 2)),
//                new TutorenZeit("Peter", LocalDateTime.of(2000, 12, 1, 6, 2)),
//                new TutorenZeit("Ulli", LocalDateTime.of(2000, 12, 1, 6, 2))
//        );
//        model.addAttribute("tutorenZeiten", zeiten);

        return "konfiguration";
    }

    @PostMapping("/konfiguration")
    public String handleConfigPost(Model model,
                                   PraktischeUbungswocheKonfigurationPostAdapter config) {
        config.create();
        return "redirect:/tutorenansicht";
    }

    @PostMapping("/tutorenZeitHinzufugen")
    public String handleAddTutor(Model model, String tutorenName, String slotZeit, String slotDatum) {
        tzService.parseAndAdd(tutorenName,slotZeit,slotDatum);

        model.addAttribute("tutorenZeiten", tzService.findAll());

        return "konfiguration";
    }

    @PostMapping("/tutorenZeitLoschen/{id}")
    public String handleDeleteTutor(Model model, @PathVariable(value="id") UUID id, HttpServletRequest req) {
        System.out.println("map = " + req.getParameterMap());

        String[] tz = req.getParameterMap().get("tzs");

        System.out.println("tz null? " + (tz == null));

        List<TutorenZeit> tutorenZeiten;

        if (tz != null) {
            tutorenZeiten =
                    Arrays.stream(tz)
                            .map(TutorenZeit::fromParseable)
                            .collect(Collectors.toList());
        } else {
            tutorenZeiten = new ArrayList<>();
        }

        //tzService.removeByID(id);

        tutorenZeiten.forEach(elem -> System.out.println(elem.toParseable()));

        model.addAttribute("tutorenZeiten", tutorenZeiten);

        return "konfiguration";
    }


    @Data
    @AllArgsConstructor
    private static class PraktischeUbungswocheKonfigurationPostAdapter {

        String name;
        int modus;

        String anmeldestartdatum;
        String anmeldestartzeit;

        String anmeldeschlussdatum;
        String anmeldeschlusszeit;

        int minPersonen;
        int maxPersonen;

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
