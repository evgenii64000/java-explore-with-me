package ru.practicum.main_service.compilation.model;

import ru.practicum.main_service.event.model.EventMapper;

import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto fromCompilationToDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream()
                        .map(event -> EventMapper.fromEventToShort(event))
                        .collect(Collectors.toList()))
                .build();
    }
}
