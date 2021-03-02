package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
class TutorenZeit {

    String name;
    LocalDateTime zeit;

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
        }
        return "So";
    }

}