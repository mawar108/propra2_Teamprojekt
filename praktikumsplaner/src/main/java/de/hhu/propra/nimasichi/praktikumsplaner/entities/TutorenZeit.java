package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TutorenZeit {

    private final String name;
    private final LocalDateTime zeit;
    private final UUID id;

    public TutorenZeit(final String name,
                       final LocalDateTime zeit) {
        this.name = name;
        this.zeit = zeit;
        this.id = UUID.randomUUID();
    }

    public static TutorenZeit fromParseable(final String fmt) {
        final var parts = fmt.split(";");

        final var time =
                LocalDateTime.of(DateService.stringToLocalDate(parts[0]),
                        DateService.stringToLocalTime(parts[1]));

        return new TutorenZeit(parts[2], time);
    }

    public static TutorenZeit from(final String tutorenName,
                                   final String slotZeit,
                                   final String slotDatum) {
        return fromParseable(slotDatum + ";" + slotZeit + ";" + tutorenName);
    }

    public String toParseable() {
        return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + name;
    }

}
