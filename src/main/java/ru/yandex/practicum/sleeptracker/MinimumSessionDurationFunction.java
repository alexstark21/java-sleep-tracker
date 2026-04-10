package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinimumSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    public static final String MINIMUM_MESSAGE = "Минимальная продолжительность сессии сна (минуты)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long minimumSessionDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStart(), session.getEnd()))
                .mapToLong(Duration::toMinutes)
                .min()
                .orElse(0);
        return new SleepAnalysisResult<>(MINIMUM_MESSAGE, minimumSessionDuration);
    }
}