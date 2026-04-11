package ru.yandex.practicum.sleeptracker.exceptions;

public class SleepTrackerException extends Exception {

    public SleepTrackerException(String message) {
        super(message);
    }

    public SleepTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}
