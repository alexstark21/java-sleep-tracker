package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class HowManySessionsForPeriodFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {

    public static final String HOW_MANY_MESSAGE = "Количество сессий сна";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult<>(HOW_MANY_MESSAGE, sleepingSessions.size());
    }
}
