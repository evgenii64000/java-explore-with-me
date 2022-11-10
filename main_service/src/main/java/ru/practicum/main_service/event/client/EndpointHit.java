package ru.practicum.main_service.event.client;

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
