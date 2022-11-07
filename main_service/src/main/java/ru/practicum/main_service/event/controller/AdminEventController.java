package ru.practicum.main_service.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.model.AdminUpdateEventRequest;
import ru.practicum.main_service.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/events")
@Validated
public class AdminEventController {

    private final EventService eventService;

    @Autowired
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Object> getEventsForAdmin(@RequestParam(name = "users") List<Long> users,
                                                    @RequestParam(name = "states") List<String> states,
                                                    @RequestParam(name = "categories") List<Long> categories,
                                                    @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                                                    @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                                                    @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(eventService.getEventsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> redactEventByAdmin(@PathVariable Long eventId,
                                                     @RequestBody AdminUpdateEventRequest updateEvent) {
        return new ResponseEntity<>(eventService.redactEventByAdmin(eventId, updateEvent),HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.publishEvent(eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/reject")
    public ResponseEntity<Object> rejectEvent(@PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.rejectEvent(eventId), HttpStatus.OK);
    }
}
