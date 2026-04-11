package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    private final HowManySessionsForPeriodFunction function = new HowManySessionsForPeriodFunction();
    private final MinimumSessionDurationFunction function2 = new MinimumSessionDurationFunction();
    private final MaximumSessionDurationFunction function3 = new MaximumSessionDurationFunction();
    private final AverageSessionDurationFunction function4 = new AverageSessionDurationFunction();
    private final BadQualitySessionsCounterFunction function5 = new BadQualitySessionsCounterFunction();
    private final SleepLessNightsCounterFunction counterFunction = new SleepLessNightsCounterFunction();
    private final ChronotypeDetectorFunction detector = new ChronotypeDetectorFunction();

    @Test
    void shouldReturnCorrectSizeWhenSessionsExist() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(7), SleepQuality.NORMAL)
        );
        SleepAnalysisResult<Integer> result = function.apply(sessions);
        assertEquals(2, result.getValue(), "Количество сессий должно быть равно 2");
    }

    @Test
    void shouldReturnZeroWhenListIsEmpty() {
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult<Integer> result = function.apply(sessions);
        assertEquals(0, result.getValue(), "Для пустого списка количество сессий должно быть 0");
    }

    @Test
    void testMinimumDurationWithMultipleSessionsShouldFindMinimum() {
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2024, 12, 1, 23, 0),
                LocalDateTime.of(2024, 12, 2, 7, 0),
                SleepQuality.NORMAL
        );

        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2024, 12, 2, 1, 0),
                LocalDateTime.of(2024, 12, 2, 4, 30),
                SleepQuality.BAD
        );

        SleepingSession session3 = new SleepingSession(
                LocalDateTime.of(2024, 12, 3, 22, 0),
                LocalDateTime.of(2024, 12, 4, 9, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> sessions = List.of(session1, session2, session3);

        SleepAnalysisResult<Long> result = function2.apply(sessions);

        assertEquals(210L, result.getValue(), "Минимальная продолжительность сессии сна (минуты)");
    }

    @Test
    void testMinimumDurationWithEmptyListShouldReturnZero() {
        List<SleepingSession> emptySessions = List.of();

        SleepAnalysisResult<Long> result = function2.apply(emptySessions);

        assertEquals(0L, result.getValue(), "Минимальная продолжительность сессии сна (минуты)");
    }

    @Test
    void testMaximumDurationWithMultipleSessionsShouldFindMinimum() {
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2024, 12, 1, 23, 0),
                LocalDateTime.of(2024, 12, 2, 7, 0),
                SleepQuality.NORMAL
        );

        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2024, 12, 2, 1, 0),
                LocalDateTime.of(2024, 12, 2, 4, 30),
                SleepQuality.BAD
        );

        SleepingSession session3 = new SleepingSession(
                LocalDateTime.of(2024, 12, 3, 22, 0),
                LocalDateTime.of(2024, 12, 4, 9, 0),
                SleepQuality.GOOD
        );

        List<SleepingSession> sessions = List.of(session1, session2, session3);

        SleepAnalysisResult<Long> result = function3.apply(sessions);

        assertEquals(660L, result.getValue(), "Максимальная продолжительность сессии сна (минуты)");
    }

    @Test
    void testMaximumDurationWithEmptyListShouldReturnZero() {
        List<SleepingSession> emptySessions = List.of();

        SleepAnalysisResult<Long> result = function3.apply(emptySessions);

        assertEquals(0L, result.getValue(), "Максимальная продолжительность сессии сна (минуты)");
    }

    @Test
    void shouldCalculateAverageForMultipleSessions() {
        LocalDateTime start1 = LocalDateTime.of(2023, 10, 1, 22, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 10, 1, 23, 0);
        LocalDateTime start2 = LocalDateTime.of(2023, 10, 2, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, 10, 3, 0, 0);
        List<SleepingSession> sessions = List.of(
                new SleepingSession(start1, end1, SleepQuality.GOOD),
                new SleepingSession(start2, end2, SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Double> result = function4.apply(sessions);

        assertEquals(90.0, result.getValue(), 0.001, "Средняя длительность должна быть 90 минут");
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult<Double> result = function4.apply(sessions);

        assertEquals(0.0, result.getValue(), "Для пустого списка среднее должно быть 0.0");
    }

    @Test
    void shouldCountOnlyBadQualitySessions() {
        LocalDateTime now = LocalDateTime.now();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(now, now.plusHours(8), SleepQuality.BAD),
                new SleepingSession(now.plusDays(1), now.plusDays(1).plusHours(7), SleepQuality.BAD),
                new SleepingSession(now.plusDays(2), now.plusDays(2).plusHours(6), SleepQuality.GOOD),
                new SleepingSession(now.plusDays(3), now.plusDays(3).plusHours(5), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Long> result = function5.apply(sessions);

        assertEquals(2L, result.getValue(), "Должно быть найдено 2 сессии с качеством BAD");
    }

    @Test
    void shouldReturnZeroWhenNoBadSessions() {
        LocalDateTime now = LocalDateTime.now();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(now, now.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(now.plusDays(1), now.plusDays(1).plusHours(9), SleepQuality.NORMAL)
        );
        SleepAnalysisResult<Long> result = function5.apply(sessions);
        assertEquals(0L, result.getValue(), "Если сессий BAD нет, результат должен быть 0");
    }

    @Test
    void shouldReturnZeroWhenUserSleepsEveryNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = counterFunction.apply(sessions);
        assertEquals(0L, result.getValue(), "Должно быть 0 бессонных ночей");
    }

    @Test
    void shouldCountSleeplessNightsWhenOnlyDaytimeSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 13, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 13, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Long> result = counterFunction.apply(sessions);
        assertEquals(2L, result.getValue(), "Должно быть 2 бессонные ночи (02.10 и 03.10)");
    }

    @Test
    void shouldCorrectHandleNoonRuleBeforeTwelve() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 8, 0),
                        LocalDateTime.of(2025, 10, 1, 9, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = counterFunction.apply(sessions);
        assertEquals(1L, result.getValue(), "Должна быть 1 бессонная ночь");
    }

    @Test
    void shouldWorkCorrectlyAcrossMonthTransition() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 31, 14, 0),
                        LocalDateTime.of(2025, 10, 31, 16, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 11, 1, 23, 0),
                        LocalDateTime.of(2025, 11, 2, 7, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = counterFunction.apply(sessions);
        assertEquals(1L, result.getValue(), "Должна быть 1 бессонная ночь при переходе месяца");
    }

    @Test
    void shouldDetectOwlChronotype() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30), SleepQuality.GOOD)
        );

        SleepAnalysisResult<String> result = detector.apply(sessions);
        assertEquals("Сова", result.getValue());
    }

    @Test
    void shouldDetectLarkChronotype() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 30), SleepQuality.GOOD)
        );

        SleepAnalysisResult<String> result = detector.apply(sessions);
        assertEquals("Жаворонок", result.getValue());
    }

    @Test
    void shouldReturnPigeonWhenCountsAreEqual() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<String> result = detector.apply(sessions);
        assertEquals("Голубь", result.getValue(), "При равном количестве типов должен быть Голубь");
    }

    @Test
    void shouldIgnoreDaytimeSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 13, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<String> result = detector.apply(sessions);
        assertEquals("Голубь", result.getValue(), "Дневные сессии не должны влиять на хронотип");
    }
}