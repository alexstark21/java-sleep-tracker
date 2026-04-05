package ru.yandex.practicum.sleeptracker.exceptions;

public class EmptySleepingSessionsException extends SleepTrackerSystemException{
    public EmptySleepingSessionsException(String message) {
        super(message);
    }

    public EmptySleepingSessionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
