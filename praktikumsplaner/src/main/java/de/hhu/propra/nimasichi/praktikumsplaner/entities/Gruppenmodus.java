package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Getter;

public enum Gruppenmodus {
    INDIVIDUAL(0),
    GRUPPE(1);

    @Getter
    private final int value;

    Gruppenmodus(final int val) {
        this.value = val;
    }

    public static Gruppenmodus from(final int modus) {
        if (modus == 0) {
            return INDIVIDUAL;
        } else {
            return GRUPPE;
        }
    }

}
