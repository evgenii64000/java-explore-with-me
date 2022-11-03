package ru.practicum.main_service.exceptions;

public class WrongStatusException extends RuntimeException {

    public WrongStatusException(final String message) {
        super(message);
    }
}
