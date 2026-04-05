package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.exceptions.EmptySleepingSessionsException;
import ru.yandex.practicum.sleeptracker.exceptions.SleepTrackerSystemException;
import ru.yandex.practicum.sleeptracker.exceptions.SleepingSessionsLoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SleepingSessionsLoader {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> loadSleepingSessions(String filePath) throws SleepTrackerSystemException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new SleepingSessionsLoadException("По данному пути файл c логом сна отсутствует: " + filePath);
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<SleepingSession> sessions = reader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(this::parseLine)
                    .collect(Collectors.toList());

            return Optional.of(sessions)
                    .filter(s -> !s.isEmpty())
                    .orElseThrow(() -> new EmptySleepingSessionsException("Файл пуст"));

        } catch (IOException e) {
            throw new SleepingSessionsLoadException("Ошибка чтения файла ", e);
        } catch (EmptySleepingSessionsException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    private SleepingSession parseLine(String line) {
        String[] split = line.split(";");
        return new SleepingSession(
                LocalDateTime.parse(split[0], FORMATTER),
                LocalDateTime.parse(split[1], FORMATTER),
                split[2]
        );
    }
}