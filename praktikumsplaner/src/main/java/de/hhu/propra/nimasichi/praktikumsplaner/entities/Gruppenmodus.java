package de.hhu.propra.nimasichi.praktikumsplaner.entities;

public enum Gruppenmodus {
    INDIVIDUAL(0),
    GRUPPE(1);

    int value;

    Gruppenmodus(int value) {
        this.value = value;
    }

    public static Gruppenmodus from(int modus) {
        if (modus == 0) {
            return INDIVIDUAL;
        } else {
            return GRUPPE;
        }
    }

}