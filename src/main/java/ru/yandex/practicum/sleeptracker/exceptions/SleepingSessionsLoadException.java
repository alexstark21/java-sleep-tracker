package ru.yandex.practicum.sleeptracker.exceptions;

public class SleepingSessionsLoadException extends SleepTrackerSystemException {
    public SleepingSessionsLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SleepingSessionsLoadException(String message) {
        super(message);
    }
}
