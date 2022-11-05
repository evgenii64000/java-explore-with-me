package ru.practicum.stats_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats_service.model.EndpointHit;
import ru.practicum.stats_service.model.Hit;
import ru.practicum.stats_service.model.HitMapper;
import ru.practicum.stats_service.model.ViewsStats;
import ru.practicum.stats_service.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatRepository statRepository;

    @Autowired
    public StatsServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public void addHit(EndpointHit endpointHit) {
        Hit hit = HitMapper.fromEndpointToHit(endpointHit, FORMAT);
        statRepository.save(hit);
    }

    @Override
    public List<ViewsStats> stats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, FORMAT);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMAT);
        List<Hit> hits;
        if (uris != null) {
            if (unique) {
                hits = statRepository.getHitsWithUriUnique(startTime, endTime, uris);
            } else {
                hits = statRepository.getHitsWithUri(startTime, endTime, uris);
            }
        } else {
            if (unique) {
                hits = statRepository.getHitsUnique(startTime, endTime);
            } else {
                hits = statRepository.getHits(startTime, endTime);
            }
        }
        List<ViewsStats> viewsStats = hits.stream()
                .map(hit -> ViewsStats.builder().app(hit.getApp()).uri(hit.getUri()).hits(0).build())
                .distinct()
                .collect(Collectors.toList());
        for (Hit hit : hits) {
            viewsStats.stream()
                    .filter(v -> v.getApp().equals(hit.getApp()) && v.getUri().equals(hit.getUri()))
                    .findFirst().ifPresent(view -> view.setHits(view.getHits() + 1));
        }
        return viewsStats;
    }
}
