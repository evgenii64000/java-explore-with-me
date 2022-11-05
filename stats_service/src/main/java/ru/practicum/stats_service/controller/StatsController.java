package ru.practicum.stats_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_service.model.EndpointHit;
import ru.practicum.stats_service.service.StatsService;

import java.util.List;

@Controller
@RequestMapping
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public void addHit(@RequestBody EndpointHit endpointHit) {
        statsService.addHit(endpointHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStat(@RequestParam String start,
                                          @RequestParam String end,
                                          @RequestParam (defaultValue = "null") List<String> uris,
                                          @RequestParam (defaultValue = "false") Boolean unique) {
        return new ResponseEntity<>(statsService.stats(start, end, uris, unique), HttpStatus.OK);
    }
}
