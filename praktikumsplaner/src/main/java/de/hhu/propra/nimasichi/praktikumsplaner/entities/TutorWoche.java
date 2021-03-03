package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorWoche {
    @Getter
    private final Map<DayOfWeek,List<LocalTime>> wochenZeiten;

    @Getter
    private final String tutorName;

    public TutorWoche(String tutorName) {
        this.wochenZeiten = new HashMap<>();
        this.tutorName = tutorName;
    }

    public void addWochenZeiten(List<TutorenZeit> tutorenZeiten) {
        for(TutorenZeit tutorenZeit : tutorenZeiten) {
            DayOfWeek dayOfWeek = tutorenZeit.getZeit().getDayOfWeek();
            var zeiten = wochenZeiten.get(dayOfWeek);
            zeiten.add(tutorenZeit.getZeit().toLocalTime());
            wochenZeiten.put(dayOfWeek,zeiten);
        }
    }

    public static List<DayOfWeek> getDaysOfWeek() {
        List<DayOfWeek> wochenTage = new ArrayList<>();
        for(int i=1; i<=5; i++) {
            wochenTage.add(DayOfWeek.of(i));
        }
        return wochenTage;
    }

}
