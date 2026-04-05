package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.exceptions.SleepTrackerSystemException;

import java.util.List;
import java.util.Scanner;

public class SleepTrackerApp {
    static List<SleepAnalysisResult<?>> functions;

    public static void main(String[] args) throws SleepTrackerSystemException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу с логом сна");
        SleepingSessionsLoader loader = new SleepingSessionsLoader();
        List<SleepingSession> sleepingSessions = loader.loadSleepingSessions(scanner.next());

        functions = List.of(new HowManySessionsForPeriodFunction().apply(sleepingSessions),
                new MinimumSessionDurationFunction().apply(sleepingSessions),
                new MaximumSessionDurationFunction().apply(sleepingSessions),
                new AverageSessionDurationFunction().apply(sleepingSessions),
                new BadQualitySessionsCounterFunction().apply(sleepingSessions),
                new SleepLessNightsCounterFunction().apply(sleepingSessions),
                new ChronotypeDetectorFunction().apply(sleepingSessions));

        functions.forEach(function -> System.out.println(function.getDescription() + ": " + function.getValue()));

    }
}