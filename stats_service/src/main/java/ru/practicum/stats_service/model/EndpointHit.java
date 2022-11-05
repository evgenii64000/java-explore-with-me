package ru.practicum.stats_service.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
