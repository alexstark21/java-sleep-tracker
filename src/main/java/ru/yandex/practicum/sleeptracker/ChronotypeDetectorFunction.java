package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeDetectorFunction implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {

    private static final LocalTime OWL_SLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {
        String chronotype = Optional.ofNullable(sessions)
                .orElse(List.of())
                .stream()
                .filter(s -> s.getFrom().toLocalDate().isBefore(s.getTill().toLocalDate()))
                .map(this::mapToType)
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()),
                        this::determineWinner
                ));

        return new SleepAnalysisResult<>("Ваш хронотип", chronotype);
    }

    private String mapToType(SleepingSession s) {
        LocalTime start = s.getFrom().toLocalTime();
        LocalTime end = s.getTill().toLocalTime();

        if (start.isAfter(OWL_SLEEP_AFTER) && end.isAfter(OWL_WAKE_AFTER)) {
            return "Сова";
        } else if (start.isBefore(LARK_SLEEP_BEFORE) && end.isBefore(LARK_WAKE_BEFORE)) {
            return "Жаворонок";
        } else {
            return "Голубь";
        }
    }

    private String determineWinner(Map<String, Long> counts) {
        long owls = counts.getOrDefault("Сова", 0L);
        long larks = counts.getOrDefault("Жаворонок", 0L);
        long pigeons = counts.getOrDefault("Голубь", 0L);

        if (owls == 0 && larks == 0 && pigeons == 0) return "Не определен";
        if (owls > larks && owls > pigeons) return "Сова";
        if (larks > owls && larks > pigeons) return "Жаворонок";

        return "Голубь";
    }
}
