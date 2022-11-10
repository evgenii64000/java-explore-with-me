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
public class EventFullDto {

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
    private Location location;
    @NotNull
    private Boolean paid;
    private String description;
    private String createdOn;
    private String publishedOn;
    private String state;
    private Integer views;
    private Integer participantLimit;
    private Integer confirmedRequests;
    private Boolean requestModeration;

}
