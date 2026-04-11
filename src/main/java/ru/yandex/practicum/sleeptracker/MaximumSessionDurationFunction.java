package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaximumSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    public static final String MAXIMUM_MESSAGE = "Максимальная продолжительность сессии сна (минуты)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long maximumSessionDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStart(), session.getEnd()))
                .mapToLong(Duration::toMinutes)
                .max()
                .orElse(0);
        return new SleepAnalysisResult<>(MAXIMUM_MESSAGE, maximumSessionDuration);
    }
}
