package ru.practicum.main_service.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.model.NewEventDto;
import ru.practicum.main_service.event.model.UpdateEventRequest;
import ru.practicum.main_service.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final EventService eventService;

    @Autowired
    public PrivateEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Object> getEventsByUser(@PathVariable Long userId,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(eventService.findEventsByUser(userId, PageRequest.of(from / size, size)), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Object> updateEventByUser(@PathVariable Long userId,
                                                    @RequestBody UpdateEventRequest updateEventRequest) {
        return new ResponseEntity<>(eventService.updateEventByUser(userId, updateEventRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createEventByUser(@PathVariable Long userId,
                                                    @RequestBody NewEventDto newEventDto) {
        return new ResponseEntity<>(eventService.createEventByUser(userId, newEventDto), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getEventByUser(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.getEventByUser(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> cancelEventByUser(@PathVariable Long userId,
                                                    @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.cancelEventByUser(userId, eventId), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> getRequestsByUser(@PathVariable Long userId,
                                                    @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.getRequestsByUser(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmRequestByUser(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return new ResponseEntity<>(eventService.confirmRequestByUser(userId, eventId, reqId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectRequestByUser(@PathVariable Long userId,
                                                      @PathVariable Long eventId,
                                                      @PathVariable Long reqId) {
        return new ResponseEntity<>(eventService.rejectRequestByUser(userId, eventId, reqId), HttpStatus.OK);
    }
}
