package ru.practicum.main_service.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.compilation.model.CompilationDto;
import ru.practicum.main_service.compilation.model.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    CompilationDto getCompilation(Long compId);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}
