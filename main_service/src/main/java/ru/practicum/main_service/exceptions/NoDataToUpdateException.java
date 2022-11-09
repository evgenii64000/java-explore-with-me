package ru.practicum.main_service.exceptions;

public class NoDataToUpdateException extends RuntimeException {

    public NoDataToUpdateException(final String message) {
        super(message);
    }
}
