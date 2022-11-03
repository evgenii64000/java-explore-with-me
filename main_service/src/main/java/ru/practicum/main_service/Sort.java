package ru.practicum.main_service;

public enum Sort {

    EVENT_DATE,
    VIEWS;

    public static Boolean checkSort(String stateToCheck) {
        for (Sort sort : values()) {
            if (sort.name().equalsIgnoreCase(stateToCheck)) {
                return true;
            }
        }
        return false;
    }
}
