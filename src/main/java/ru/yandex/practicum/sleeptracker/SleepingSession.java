package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime from, LocalDateTime till, SleepQuality sleepQuality) {
        this.start = from;
        this.end = till;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "from=" + start +
                ", till=" + end +
                ", sleepQuality='" + sleepQuality + '\'' +
                '}';
    }
}
