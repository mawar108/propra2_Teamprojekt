package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Data;
import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TutorenZeit {

    String name;
    LocalDateTime zeit;
    UUID id;

    public TutorenZeit(String name, LocalDateTime zeit) {
        this.name = name;
        this.zeit = zeit;
        this.id = UUID.randomUUID();
    }

    public String zeitFormattiert() {
        return dayOfWeekDeutsch(zeit.getDayOfWeek()) + " " + zeit.toLocalTime().toString();
    }

    private String dayOfWeekDeutsch(DayOfWeek day) {
        switch (day) {
            case MONDAY: return "Mo";
            case TUESDAY: return "Di";
            case WEDNESDAY: return "Mi";
            case THURSDAY: return "Do";
            case FRIDAY: return "Fr";
            case SATURDAY: return "Sa";
            default: return "So";
        }
    }

    public static TutorenZeit fromParseable(String fmt) {
        var parts = fmt.split(";");

        LocalDateTime zeit =
                LocalDateTime.of(DateService.stringToLocalDate(parts[0]),
                        DateService.stringToLocalTime(parts[1]));

        return new TutorenZeit(parts[2], zeit);
    }

    public static TutorenZeit from(String tutorenName, String slotZeit, String slotDatum) {
        return fromParseable(slotDatum + ";" + slotZeit + ";" + tutorenName);
    }

    public String toParseable() {
        return zeit.toLocalDate() + ";" + zeit.toLocalTime() + ";" + name;
    }

}