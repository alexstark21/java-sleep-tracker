package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.yandex.practicum.sleeptracker.Chronotype.*;

public class ChronotypeDetectorFunction implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {

    private static final LocalTime OWL_SLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);

    public static final String CHRONOTYPE_MESSAGE = "Ваш хронотип";

    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {

        String chronotype = Optional.ofNullable(sessions)
                .orElse(List.of())
                .stream()
                .filter(s -> s.getStart().toLocalDate().isBefore(s.getEnd().toLocalDate())
                        || s.getStart().toLocalTime().isBefore(LocalTime.of(6, 0)))
                .map(this::mapToType)
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()),
                        this::determineWinner
                ));

        return new SleepAnalysisResult<>(CHRONOTYPE_MESSAGE, chronotype);
    }

    private String mapToType(SleepingSession s) {
        LocalTime start = s.getStart().toLocalTime();
        LocalTime end = s.getEnd().toLocalTime();

        if ((start.isAfter(OWL_SLEEP_AFTER) || start.isBefore(OWL_WAKE_AFTER)) && end.isAfter(OWL_WAKE_AFTER)) {
            return OWL.getName();
        } else if (start.isBefore(LARK_SLEEP_BEFORE) && end.isBefore(LARK_WAKE_BEFORE)) {
            return LARK.getName();
        } else {
            return PIGEON.getName();
        }
    }

    private String determineWinner(Map<String, Long> counts) {
        long owls = counts.getOrDefault(OWL.getName(), 0L);
        long larks = counts.getOrDefault(LARK.getName(), 0L);
        long pigeons = counts.getOrDefault(PIGEON.getName(), 0L);

        if (owls == 0 && larks == 0 && pigeons == 0) return PIGEON.getName();
        if (owls > larks && owls > pigeons) return OWL.getName();
        if (larks > owls && larks > pigeons) return LARK.getName();

        return PIGEON.getName();
    }
}
