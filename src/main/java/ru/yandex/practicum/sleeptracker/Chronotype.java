package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String name;

    Chronotype(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
