package ru.practicum.main_service.event.model;

import lombok.*;
import ru.practicum.main_service.Status;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String annotation;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private Integer confirmedRequests;
    private Integer views;
    @Enumerated(EnumType.STRING)
    private Status state;
    private Long rating;
}
