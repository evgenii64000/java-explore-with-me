package ru.practicum.main_service.event.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

    private String title;
    private String annotation;
    private Long category;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private String description;
    private Integer participantLimit;
    private Boolean requestModeration;
}
