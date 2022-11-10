package ru.practicum.main_service.exceptions;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(final String message) {
        super(message);
    }
}
