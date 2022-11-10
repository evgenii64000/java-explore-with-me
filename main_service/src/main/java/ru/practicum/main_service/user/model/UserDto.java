package ru.practicum.main_service.user.model;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotNull
    private String name;
    @Email
    private String email;
}
