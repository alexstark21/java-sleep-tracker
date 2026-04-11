package ru.yandex.practicum.sleeptracker.exceptions;

public class SleepTrackerSystemException extends SleepTrackerException {

    public SleepTrackerSystemException(String message) {
        super(message);
    }

    public SleepTrackerSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
