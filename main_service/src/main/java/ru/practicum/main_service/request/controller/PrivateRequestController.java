package ru.practicum.main_service.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.request.service.RequestService;

import javax.validation.constraints.Positive;

@Controller
@RequestMapping(path = "/users/{userId}/requests")
@Validated
public class PrivateRequestController {

    private final RequestService requestService;

    @Autowired
    public PrivateRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@PathVariable Long userId) {
        return new ResponseEntity<>(requestService.getRequests(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@PathVariable Long userId,
                                                @Positive @RequestParam Long eventId) {
        return new ResponseEntity<>(requestService.createRequest(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable Long userId,
                                                @PathVariable Long requestId) {
        return new ResponseEntity<>(requestService.cancelRequest(userId, requestId), HttpStatus.OK);
    }
}
