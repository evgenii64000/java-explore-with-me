package ru.practicum.main_service.exceptions;

public class AboveLimitException extends RuntimeException {

    public AboveLimitException(final String message) {
        super(message);
    }
}