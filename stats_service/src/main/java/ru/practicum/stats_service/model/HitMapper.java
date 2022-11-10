package ru.practicum.stats_service.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {

    public static Hit fromEndpointToHit(EndpointHit endpointHit, DateTimeFormatter formatter) {
        return Hit.builder()
                .app(endpointHit.getApp())
                .ip(endpointHit.getIp())
                .uri(endpointHit.getUri())
                .timestamp(LocalDateTime.parse(endpointHit.getTimestamp(), formatter))
                .build();
    }
}
