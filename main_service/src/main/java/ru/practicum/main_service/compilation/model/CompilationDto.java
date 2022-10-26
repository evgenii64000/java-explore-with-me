package ru.practicum.main_service.compilation.model;

import lombok.*;
import ru.practicum.main_service.event.model.EventShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    @Positive
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotNull
    private String title;
    private List<EventShortDto> events;
}
