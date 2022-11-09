package ru.practicum.main_service.event.model;

public class LocationMapper {

    public static Location toDto(LocationEntity locationEntity) {
        return Location.builder()
                .id(locationEntity.getId())
                .lat(locationEntity.getLat())
                .lon(locationEntity.getLon())
                .build();
    }

    public static LocationEntity toEntity(Location location) {
        return LocationEntity.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
