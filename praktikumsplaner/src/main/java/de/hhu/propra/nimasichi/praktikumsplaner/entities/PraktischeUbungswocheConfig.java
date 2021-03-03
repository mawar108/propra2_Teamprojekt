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

    public static PraktischeUbungswocheConfig makeConfig(
            final String name,
            final int modus,
            final String anmeldestartdatum,
            final String anmeldestartzeit,
            final String anmeldeschlussdatum,
            final String anmeldeschlusszeit,
            final int minPersonen,
            final int maxPersonen) {

        final var praktischeUbungswocheConfig =
                new PraktischeUbungswocheConfig();

        praktischeUbungswocheConfig.setName(name);

        praktischeUbungswocheConfig.setAnmeldestart(
                DateService
                        .mergeDateTimeStrings(
                            anmeldestartdatum,
                            anmeldestartzeit)
        );

        praktischeUbungswocheConfig.setAnmeldeschluss(
                DateService
                        .mergeDateTimeStrings(
                                anmeldeschlussdatum,
                                anmeldeschlusszeit)
        );

        praktischeUbungswocheConfig
                .setModus(Gruppenmodus.from(modus));
        praktischeUbungswocheConfig
                .setMinPersonen(minPersonen);
        praktischeUbungswocheConfig
                .setMaxPersonen(maxPersonen);

        return praktischeUbungswocheConfig;
    }
}
