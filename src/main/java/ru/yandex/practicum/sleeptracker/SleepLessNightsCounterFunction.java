package ru.yandex.practicum.sleeptracker;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SleepLessNightsCounterFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final LocalTime NOON = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return Optional.ofNullable(sessions)
                .filter(s -> !s.isEmpty())
                .map(this::calculateSleeplessNights)
                .orElse(new SleepAnalysisResult<>("Количество бессонных ночей", 0L));
    }

    private SleepAnalysisResult<Long> calculateSleeplessNights(List<SleepingSession> sessions) {
        LocalDate startNight = sessions.stream().findFirst()
                .map(s -> s.getFrom().toLocalTime().isBefore(NOON)
                        ? s.getFrom().toLocalDate()
                        : s.getFrom().toLocalDate().plusDays(1))
                .orElseThrow();

        LocalDate endNight = sessions.getLast().getTill().toLocalDate();

        Period period = Period.between(startNight, endNight.plusDays(1));

        long nightsWithSleep = sessions.stream()
                .filter(s -> s.getFrom().toLocalDate().isBefore(s.getTill().toLocalDate())
                        || s.getFrom().toLocalTime().isBefore(NIGHT_END))
                .map(s -> s.getTill().toLocalDate())
                .distinct()
                .filter(date -> !date.isBefore(startNight) && !date.isAfter(endNight))
                .count();

        return new SleepAnalysisResult<>("Количество бессонных ночей", period.getDays() - nightsWithSleep);
    }
}
