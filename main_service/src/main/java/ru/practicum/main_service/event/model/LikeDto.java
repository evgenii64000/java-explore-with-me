package ru.practicum.main_service.event.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {

    private Long user;
    private Long event;
    private Boolean rate;
}
