package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsCounterFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    public static final String BAD_MESSAGE = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long badQualityCounter = sleepingSessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD).count();

        return new SleepAnalysisResult<>(BAD_MESSAGE, badQualityCounter);
    }
}
