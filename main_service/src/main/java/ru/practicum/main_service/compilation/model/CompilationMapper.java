package ru.practicum.main_service.compilation.model;

import ru.practicum.main_service.event.model.EventMapper;

import java.util.Collections;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto fromCompilationToDto(Compilation compilation) {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
        if (compilation.getEvents() == null) {
            compilationDto.setEvents(Collections.emptyList());
        } else {
            compilationDto.setEvents(compilation.getEvents().stream()
                    .map(event -> EventMapper.fromEventToShort(event))
                    .collect(Collectors.toList()));
        }
        return compilationDto;
    }
}
