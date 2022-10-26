package ru.practicum.main_service.event.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @NotNull
    private String title;
    @NotNull
    private String annotation;
    @Positive
    private Long category;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    @NotNull
    private String description;
    private Integer participantLimit;
    private Boolean requestModeration;
}
