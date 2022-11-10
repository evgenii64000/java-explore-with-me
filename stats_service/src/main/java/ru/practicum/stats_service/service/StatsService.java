package ru.practicum.stats_service.service;

import ru.practicum.stats_service.model.EndpointHit;
import ru.practicum.stats_service.model.ViewsStats;

import java.util.List;

public interface StatsService {

    void addHit(EndpointHit endpointHit);

    List<ViewsStats> stats(String start, String end, List<String> uris, Boolean unique);
}
