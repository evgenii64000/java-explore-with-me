package ru.practicum.main_service.event.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Long id;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
}
