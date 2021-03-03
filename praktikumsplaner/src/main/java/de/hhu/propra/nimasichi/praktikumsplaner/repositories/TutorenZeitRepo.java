package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.controller.TutorenZeit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TutorenZeitRepo {
    private List<TutorenZeit> tutorenZeitList;

    public TutorenZeitRepo() {
        this.tutorenZeitList = new ArrayList<>();
    }

    public List<TutorenZeit> findAll() {
        return tutorenZeitList;
    }

    public TutorenZeit findByID(UUID id) {
        return tutorenZeitList.stream()
                .filter(x->x.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public void add(TutorenZeit tz) {
        tutorenZeitList.add(tz);
    }

    public void removeById(UUID id) {
        tutorenZeitList.remove(findByID(id));
    }

    public void sortByDate() {
        tutorenZeitList.sort(Comparator.comparing(o->o.getZeit()));
    }



}
