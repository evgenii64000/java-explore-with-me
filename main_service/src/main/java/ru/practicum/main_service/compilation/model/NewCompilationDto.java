package ru.practicum.main_service.compilation.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    private Boolean pinned;
    @NotNull
    private String title;
    private List<Long> events;
}
