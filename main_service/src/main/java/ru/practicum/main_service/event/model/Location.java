package ru.practicum.main_service.event.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Float lat;
    private Float lon;
}
