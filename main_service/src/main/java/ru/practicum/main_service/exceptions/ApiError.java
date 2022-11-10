package ru.practicum.main_service.exceptions;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String status;
    private List<String> errors;
    private String message;
    private String reason;
    private String timestamp;
}
