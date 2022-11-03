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
import ru.practicum.main_service.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequestMapping(path = "/events")
@Validated
public class PublicEventController {

    private final EventService eventService;

    @Autowired
    public PublicEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam(name = "text") String text,
                                            @RequestParam(name = "categories") List<Long> categories,
                                            @RequestParam(name = "paid") Boolean paid,
                                            @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                                            @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                                            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(name = "sort") String sort,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size)
    /**
     * // TODO проверить ограничения.
     */
    {
        return new ResponseEntity<>(eventService.findEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.findEvent(id), HttpStatus.OK);
    }
}
