package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    LocalDateTime from;
    LocalDateTime till;
    String condition;

    public SleepingSession(LocalDateTime from, LocalDateTime till, String condition) {
        this.from = from;
        this.till = till;
        this.condition = condition;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTill() {
        return till;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "from=" + from +
                ", till=" + till +
                ", condition='" + condition + '\'' +
                '}';
    }
}
