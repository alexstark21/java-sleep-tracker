package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class HowManySessionsForPeriodFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult<>("Количество сессий сна", sleepingSessions.size());
    }
}
