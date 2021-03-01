package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Zeitslot {
    LocalDateTime ubungsAnfang;
    List<String> tutoren;

    public String tutorenAuflistung() {
        return String.join(", ", tutoren);
    }
}
