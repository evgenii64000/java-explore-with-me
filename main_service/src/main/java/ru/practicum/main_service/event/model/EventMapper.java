package ru.practicum.main_service.event.model;

import ru.practicum.main_service.Status;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.model.CategoryMapper;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.model.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    public static Event fromNewToEvent(NewEventDto newEventDto, Category category, User initiator, Location location,
                                       LocalDateTime eventDate) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .eventDate(eventDate)
                .location(LocationMapper.toEntity(location))
                .initiator(initiator)
                .paid(newEventDto.getPaid())
                .description(newEventDto.getDescription())
                .views(0)
                .createdOn(LocalDateTime.now())
                .publishedOn(null)
                .confirmedRequests(0)
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(Status.PENDING)
                .build();
    }

    public static EventFullDto fromEventToFull(Event event) {
        EventFullDto eventFullDto =  EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategoryToDto(event.getCategory()))
                .initiator(UserMapper.fromUserToShort(event.getInitiator()))
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .location(LocationMapper.toDto(event.getLocation()))
                .paid(event.getPaid())
                .description(event.getDescription())
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .state(event.getState().toString())
                .views(event.getViews())
                .participantLimit(event.getParticipantLimit())
                .confirmedRequests(event.getConfirmedRequests())
                .requestModeration(event.getRequestModeration())
                .build();
        if (!eventFullDto.getState().equals("PUBLISHED")) {
            eventFullDto.setPublishedOn(null);
        } else {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return eventFullDto;
    }

    public static EventShortDto fromEventToShort(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategoryToDto(event.getCategory()))
                .initiator(UserMapper.fromUserToShort(event.getInitiator()))
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .paid(event.getPaid())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }
}
