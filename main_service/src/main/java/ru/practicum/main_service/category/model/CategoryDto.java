package ru.practicum.main_service.category.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @Positive
    private Long id;
    @NotNull
    private String name;
}
