package ru.practicum.main_service.compilation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/compilations")
@Validated
public class PublicCompilationController {

//    @GetMapping
//    public ResponseEntity<Object> getCompilations(@RequestParam(name = "pinned") Boolean pinned,
//                                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
//                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
//
//    }
//
//    @GetMapping("/{compId}")
//    public ResponseEntity<Object> getCompilation(@PathVariable Long compId) {
//
//    }
}
