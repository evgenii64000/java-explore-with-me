package ru.practicum.main_service.user.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    @Positive
    private Long id;
    @NotNull
    private String name;
    private Long rating;
}
