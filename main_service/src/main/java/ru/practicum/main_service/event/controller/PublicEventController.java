package ru.practicum.main_service.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/events")
@Validated
public class PublicEventController {

//    @GetMapping
//    public ResponseEntity<Object> getEvents(@RequestParam(name = "text") String text,
//                                            @RequestParam(name = "categories") List<Long> categories,
//                                            @RequestParam(name = "paid") Boolean paid,
//                                            @RequestParam(name = "rangeStart") String rangeStart,
//                                            @RequestParam(name = "rangeEnd") String rangeEnd,
//                                            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
//                                            @RequestParam(name = "sort") String sort,
//                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
//                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
//
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getEvent(@PathVariable Long id) {
//
//    }
}
