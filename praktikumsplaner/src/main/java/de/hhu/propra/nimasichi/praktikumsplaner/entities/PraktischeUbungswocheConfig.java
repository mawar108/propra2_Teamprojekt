package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PraktischeUbungswocheConfig {
    private LocalDateTime anmeldestart;
    private LocalDateTime anmeldeschluss;
    private String name;
    private List<TutorenZeit> zeitslots = new ArrayList<>();
    private Gruppenmodus modus = Gruppenmodus.from(0);
    private int minPersonen;
    private int maxPersonen;


    public static PraktischeUbungswocheConfig makeConfigAndFillZeiten(
            final FormParams params,
            final List<TutorenZeit> tutorenZeiten) {
        final var praUbungswocheCfg =
                new PraktischeUbungswocheConfig();

        praUbungswocheCfg.setName(params.getName());

        praUbungswocheCfg.setAnmeldestart(
                DateService
                        .mergeDateTimeStrings(
                                params.getAnStartdatum(),
                                params.getAnStartzeit())
        );

        praUbungswocheCfg.setAnmeldeschluss(
                DateService
                        .mergeDateTimeStrings(
                                params.getAnSchlussdatum(),
                                params.getAnSchlusszeit())
        );

        praUbungswocheCfg
                .setModus(Gruppenmodus.from(params.getModus()));
        praUbungswocheCfg
                .setMinPersonen(params.getMinPersonen());
        praUbungswocheCfg
                .setMaxPersonen(params.getMaxPersonen());

        praUbungswocheCfg.setZeitslots(tutorenZeiten);
        return praUbungswocheCfg;
    }

    public void addZeitslot(final TutorenZeit zeitslot) {
        zeitslots.add(zeitslot);
    }

    public void removeZeitslot(final TutorenZeit tutorenZeit) {
        zeitslots.remove(tutorenZeit);
    }
}
