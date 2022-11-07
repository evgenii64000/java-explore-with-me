package ru.practicum.main_service.event.service;

import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.request.model.ParticipationRequestDto;

import java.util.List;

public interface EventService {

    List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                                   Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto findEvent(Long id);

    List<EventShortDto> findEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto createEventByUser(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByUser(Long userId, Long eventId);

    EventFullDto cancelEventByUser(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId);

    ParticipationRequestDto confirmRequestByUser(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequestByUser(Long userId, Long eventId, Long reqId);

    EventFullDto redactEventByAdmin(Long eventId, AdminUpdateEventRequest updateEvent);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<EventFullDto> getEventsForAdmin(List<Long> users, List<String> states, List<Long> categories,
                                          String rangeStart, String rangeEnd, Integer from, Integer size);
}
