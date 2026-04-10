package ru.yandex.practicum.sleeptracker;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SleepLessNightsCounterFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    public static final String SLEEPLESS_MESSAGE = "Количество бессонных ночей";

    private static final LocalTime NOON = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return Optional.ofNullable(sessions)
                .filter(s -> !s.isEmpty())
                .map(this::calculateSleeplessNights)
                .orElse(new SleepAnalysisResult<>(SLEEPLESS_MESSAGE, 0L));
    }

    private SleepAnalysisResult<Long> calculateSleeplessNights(List<SleepingSession> sessions) {
        LocalDate startNight = sessions.getFirst().getStart().toLocalTime().isBefore(NOON)
                ? sessions.getFirst().getStart().toLocalDate().plusDays(1)
                : sessions.getFirst().getStart().toLocalDate();

        LocalDate endNight = sessions.getLast().getStart().toLocalDate();

        Period period = Period.between(startNight, endNight.plusDays(1));

        long nightsWithSleep = sessions.stream()
                .filter(s -> s.getStart().toLocalDate().isBefore(s.getEnd().toLocalDate())
                        || (s.getStart().toLocalTime().isBefore(NIGHT_END)
                        && s.getEnd().toLocalTime().isAfter(LocalTime.MIDNIGHT)))
                .map(s -> s.getStart().toLocalTime().isBefore(NIGHT_END)
                        ? s.getStart().toLocalDate().minusDays(1)
                        : s.getStart().toLocalDate())
                .distinct()
                .filter(date -> !date.isBefore(startNight) && !date.isAfter(endNight))
                .count();

        return new SleepAnalysisResult<>(SLEEPLESS_MESSAGE, period.getDays() - nightsWithSleep);
    }
}
