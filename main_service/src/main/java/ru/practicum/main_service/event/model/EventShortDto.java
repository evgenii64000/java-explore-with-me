package ru.practicum.main_service.event.model;

import lombok.*;
import ru.practicum.main_service.category.model.CategoryDto;
import ru.practicum.main_service.user.model.UserShortDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private String eventDate;
    @NotNull
    private Boolean paid;
    private Integer views;
    private Integer confirmedRequests;
    private Long rating;
}
