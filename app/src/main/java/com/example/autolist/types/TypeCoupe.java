package com.example.autolist.types;

/**
 * Created by Andrei on 06.08.2018.
 */

public enum TypeCoupe {
    SEDAN(1),
    PICKUP(2),
    CROSSOVER(3),
    MINIVAN(4),
    HATCHBACK(5),
    CABRIOLET(6);

    public int value() {
        return value;
    }
    private final int value;


    TypeCoupe(int value) {
        this.value = value;
    }
}
