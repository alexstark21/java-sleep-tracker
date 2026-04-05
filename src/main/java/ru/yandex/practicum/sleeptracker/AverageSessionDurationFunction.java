package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sleepingSessions) {
        double averageSessionDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getFrom(), session.getTill()))
                .mapToDouble(Duration::toMinutes)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult<>("Средняя продолжительность сессии сна (минуты)", averageSessionDuration);
    }
}
