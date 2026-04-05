package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsCounterFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long badQualityCounter = sleepingSessions.stream()
                .filter(session -> "BAD".equalsIgnoreCase(session.getCondition())).count();

        return new SleepAnalysisResult<>("Количество сессий с плохим качеством сна", badQualityCounter);
    }
}
