package ru.practicum.main_service.event.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    @NotNull
    private Long eventId;
    private String title;
    private String annotation;
    private Long category;
    private String eventDate;
    private Boolean paid;
    private String description;
    private Integer participantLimit;
}
