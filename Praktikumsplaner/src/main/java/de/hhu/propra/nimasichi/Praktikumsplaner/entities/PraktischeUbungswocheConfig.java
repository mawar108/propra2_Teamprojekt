package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;


@Data
public class PraktischeUbungswocheConfig {
    //LocalDate wochenstart; // TODO: Brauchen wir das?
    LocalDateTime anmeldestart;
    LocalDateTime anmeldeschluss;
    String name;
    List<Zeitslot> zeitslots;
    Gruppenmodus modus;
    int minPersonen;
    int maxPersonen;
}
