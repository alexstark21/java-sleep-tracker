package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinimumSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long minimumSessionDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getFrom(), session.getTill()))
                .mapToLong(Duration::toMinutes)
                .min()
                .orElse(0);
        return new SleepAnalysisResult<>("Минимальная продолжительность сессии сна (минуты)", minimumSessionDuration);
    }
}
