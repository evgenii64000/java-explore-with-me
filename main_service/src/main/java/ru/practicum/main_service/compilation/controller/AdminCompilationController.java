package ru.practicum.main_service.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.compilation.model.NewCompilationDto;
import ru.practicum.main_service.compilation.service.CompilationService;

@Controller
@RequestMapping(path = "/admin/compilations")
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @Autowired
    public AdminCompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    public ResponseEntity<Object> createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return new ResponseEntity<>(compilationService.createCompilation(newCompilationDto), HttpStatus.OK);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Object> unpinCompilation(@PathVariable Long compId) {
        compilationService.unpinCompilation(compId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Object> pinCompilation(@PathVariable Long compId) {
        compilationService.pinCompilation(compId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
