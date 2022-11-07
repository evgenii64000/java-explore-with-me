package ru.practicum.main_service.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.main_service.event.client.StatsClient;
import ru.practicum.main_service.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequestMapping(path = "/events")
@Validated
public class PublicEventController {

    private final EventService eventService;
    private final StatsClient statsClient;

    @Autowired
    public PublicEventController(EventService eventService, StatsClient statsClient) {
        this.eventService = eventService;
        this.statsClient = statsClient;
    }

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam(name = "text") String text,
                                            @RequestParam(name = "categories") List<Long> categories,
                                            @RequestParam(name = "paid") Boolean paid,
                                            @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                                            @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                                            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(name = "sort", defaultValue = "EVENT_DATE") String sort,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                            HttpServletRequest request)
    {
        statsClient.addHit(request.getRequestURI(), request.getRemoteAddr());
        return new ResponseEntity<>(eventService.findEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable Long id,
                                           HttpServletRequest request) {
        statsClient.addHit(request.getRequestURI(), request.getRemoteAddr());
        return new ResponseEntity<>(eventService.findEvent(id), HttpStatus.OK);
    }
}
