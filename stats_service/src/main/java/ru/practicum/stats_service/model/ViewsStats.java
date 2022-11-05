package ru.practicum.stats_service.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewsStats {

    private String app;
    private String uri;
    private Integer hits;
}
