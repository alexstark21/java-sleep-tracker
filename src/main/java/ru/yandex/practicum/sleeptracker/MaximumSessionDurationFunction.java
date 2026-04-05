package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaximumSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long maximumSessionDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getFrom(), session.getTill()))
                .mapToLong(Duration::toMinutes)
                .max()
                .orElse(0);
        return new SleepAnalysisResult<>("Максимальная продолжительность сессии сна (минуты)", maximumSessionDuration);
    }
}
