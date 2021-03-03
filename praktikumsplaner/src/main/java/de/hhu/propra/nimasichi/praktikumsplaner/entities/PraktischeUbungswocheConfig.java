package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PraktischeUbungswocheConfig {
    LocalDateTime anmeldestart;
    LocalDateTime anmeldeschluss;
    String name;
    List<TutorenZeit> zeitslots = new ArrayList<>();
    Gruppenmodus modus = Gruppenmodus.from(0);
    int minPersonen;
    int maxPersonen;

    public static PraktischeUbungswocheConfig makeConfig(final String name,
                                                         final int modus,
                                                         final String anmeldestartdatum,
                                                         final String anmeldestartzeit,
                                                         final String anmeldeschlussdatum,
                                                         final String anmeldeschlusszeit,
                                                         final int minPersonen,
                                                         final int maxPersonen) {

        final var praktischeUbungswocheConfig = new PraktischeUbungswocheConfig();

        praktischeUbungswocheConfig.setName(name);

        praktischeUbungswocheConfig.setAnmeldestart(
                DateService.mergeDateTimeStrings(anmeldestartdatum, anmeldestartzeit)
        );

        praktischeUbungswocheConfig.setAnmeldeschluss(
                DateService.mergeDateTimeStrings(anmeldeschlussdatum, anmeldeschlusszeit)
        );

        praktischeUbungswocheConfig.setModus(Gruppenmodus.from(modus));
        praktischeUbungswocheConfig.setMinPersonen(minPersonen);
        praktischeUbungswocheConfig.setMaxPersonen(maxPersonen);

        return praktischeUbungswocheConfig;
    }
}
