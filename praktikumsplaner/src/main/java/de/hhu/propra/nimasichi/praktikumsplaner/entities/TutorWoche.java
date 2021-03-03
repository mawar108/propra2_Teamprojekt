package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class TutorWoche {
    @Getter
    private final Map<DayOfWeek, List<LocalTime>> wochenZeiten;

    @Getter
    private final String tutorName;

    public TutorWoche(String tutorName) {
        this.wochenZeiten = new HashMap<>();
        this.tutorName = tutorName;

        initializeWochenZeitenMap();
    }

    private void initializeWochenZeitenMap() {
        for (var dayOfWeek :
                DateService.getDaysOfWeekUntil(DayOfWeek.FRIDAY.getValue())) {
            wochenZeiten.put(dayOfWeek, new ArrayList<>());
        }
    }

    public void addWochenZeiten(List<TutorenZeit> tutorenZeiten) {
        for (var tutorenZeit : tutorenZeiten) {
            var dayOfWeek = tutorenZeit.getZeit().getDayOfWeek();
            var zeiten = wochenZeiten.get(dayOfWeek);

            zeiten.add(tutorenZeit.getZeit().toLocalTime());
            wochenZeiten.put(dayOfWeek,zeiten);
        }
    }

}
