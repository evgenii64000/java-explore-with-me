package ru.practicum.main_service.event.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.Sort;
import ru.practicum.main_service.Status;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.repository.CategoryRepository;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.event.repository.EventRepository;
import ru.practicum.main_service.exceptions.*;
import ru.practicum.main_service.request.model.ParticipationRequest;
import ru.practicum.main_service.request.model.ParticipationRequestDto;
import ru.practicum.main_service.request.model.RequestMapper;
import ru.practicum.main_service.request.repository.RequestRepository;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationService locationService;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                            CategoryRepository categoryRepository, RequestRepository requestRepository,
                            LocationService locationService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.locationService = locationService;
    }

    @Override
    public EventFullDto findEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + id + " was not found.");
        }
        return EventMapper.fromEventToFull(event.get());
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                                          Boolean onlyAvailable, String sort, Integer from, Integer size) {


        if (categories.isEmpty()) {
            throw new WrongIdException("Отсутствуют идентификаторы категорий");
        }
        LocalDateTime start;
        if (rangeStart.isEmpty()) {
            start = LocalDateTime.now();
        } else {
            start = LocalDateTime.parse(rangeStart, FORMAT);
        }
        LocalDateTime end = null;
        if (!rangeEnd.isEmpty()) {
            end = LocalDateTime.parse(rangeEnd, FORMAT);
        }
        if (end != null && end.isBefore(start)) {
            throw new WrongTimeException("Задан неверный интервал времени");
        }
        List<Event> events = null;
        if (onlyAvailable && end != null) {
            events = eventRepository.findEventsAvailableRange(text, categories, paid, start, end, PageRequest.of(from / size, size)).toList();
        }
        if (onlyAvailable && end == null) {
            events = eventRepository.findEventsAvailable(text, categories, paid, start, PageRequest.of(from / size, size)).toList();
        }
        if (!onlyAvailable && end != null) {
            events = eventRepository.findEventsRange(text, categories, paid, start, end, PageRequest.of(from / size, size)).toList();
        }
        if (!onlyAvailable && end == null) {
            events = eventRepository.findEvents(text, categories, paid, start, PageRequest.of(from / size, size)).toList();
        }
        if (Sort.valueOf(sort).equals(Sort.EVENT_DATE)) {
            return events.stream()
                    .sorted(Comparator.comparing(Event::getEventDate))
                    .map(event -> EventMapper.fromEventToShort(event))
                    .collect(Collectors.toList());
        } else {
            return events.stream()
                    .sorted(Comparator.comparing(Event::getViews))
                    .map(event -> EventMapper.fromEventToShort(event))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<EventShortDto> findEventsByUser(Long userId, Integer from, Integer size) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Event with id=" + userId + " was not found.");
        }
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size)).toList();
        return events.stream().map(event -> EventMapper.fromEventToShort(event)).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Event with id=" + userId + " was not found.");
        }
        Optional<Event> eventToUpdate = eventRepository.findById(updateEventRequest.getEventId());
        if ((eventToUpdate.isEmpty())) {
            throw new NotFoundException("Event with id=" + updateEventRequest.getEventId() + " was not found.");
        }
        Event changedEvent = eventToUpdate.get();
        if (!user.get().equals(changedEvent.getInitiator())) {
            throw new WrongIdException("Событие может редактировать только инициатор");
        }
        if (changedEvent.getState().equals(Status.CANCELED) || changedEvent.getState().equals(Status.PENDING)) {
            if (updateEventRequest.getTitle() != null) {
                changedEvent.setTitle(updateEventRequest.getTitle());
            }
            if (updateEventRequest.getAnnotation() != null) {
                changedEvent.setAnnotation(updateEventRequest.getAnnotation());
            }
            if (updateEventRequest.getCategory() != null) {
                Optional<Category> category = categoryRepository.findById(updateEventRequest.getCategory());
                if (category.isPresent()) {
                    changedEvent.setCategory(category.get());
                } else {
                    throw new NotFoundException("Category with id=" + updateEventRequest.getCategory() + " was not found.");
                }
            }
            if (updateEventRequest.getEventDate() != null) {
                LocalDateTime newEventDate = LocalDateTime.parse(updateEventRequest.getEventDate(), FORMAT);
                if (newEventDate.isBefore(LocalDateTime.now().plusHours(2L))) {
                    throw new WrongTimeException("Переделать сообщение об ошибке");
                } else {
                    changedEvent.setEventDate(newEventDate);
                }
            }
            if (updateEventRequest.getPaid() != null) {
                changedEvent.setPaid(updateEventRequest.getPaid());
            }
            if (updateEventRequest.getDescription() != null) {
                changedEvent.setDescription(updateEventRequest.getDescription());
            }
            if (updateEventRequest.getParticipantLimit() != null) {
                changedEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());
            }
            changedEvent.setState(Status.PENDING);
            eventRepository.save(changedEvent);
            return EventMapper.fromEventToFull(changedEvent);
        } else {
            throw new RuntimeException("поменять ошибку");
        }
    }

    /**
     * // TODO добавить проверку на владельца.
     */

    @Override
    public EventFullDto createEventByUser(Long userId, NewEventDto newEventDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Event with id=" + userId + " was not found.");
        }
        LocalDateTime eventDate = LocalDateTime.parse(newEventDto.getEventDate(), FORMAT);
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2L))) {
            throw new WrongTimeException("Время начала события должно быть не ранее, чем через 2 часа");
        }
        Optional<Category> category = categoryRepository.findById(newEventDto.getCategory());
        if (category.isEmpty()) {
            throw new NotFoundException("Category with id=" + newEventDto.getCategory() + " was not found.");
        }
        Location location = locationService.save(newEventDto.getLocation());
        Event eventToSave = EventMapper.fromNewToEvent(newEventDto, category.get(), user.get(), location, eventDate);
        Event event = eventRepository.save(eventToSave);
        return EventMapper.fromEventToFull(event);
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        if (!event.get().getInitiator().equals(user.get())) {
            throw new WrongIdException("Событие пытается просмотреть не инициатор");
        }
        return EventMapper.fromEventToFull(event.get());
    }

    @Override
    public EventFullDto cancelEventByUser(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Event eventToCancel = event.get();
        if (!eventToCancel.getInitiator().equals(user.get())) {
            throw new WrongIdException("Событие пытается отменить не инициатор");
        }
        if (!eventToCancel.getState().equals(Status.PENDING)) {
            throw new WrongStatusException("Нельзя отменить опубликованное событие");
        }
        eventToCancel.setState(Status.CANCELED);
        return EventMapper.fromEventToFull(eventRepository.save(eventToCancel));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        List<ParticipationRequest> requests = requestRepository.findAllByEvent_Id(eventId);
        return requests.stream().map(request -> RequestMapper.fromRequestToDto(request)).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequestByUser(Long userId, Long eventId, Long reqId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Optional<ParticipationRequest> request = requestRepository.findById(reqId);
        if (request.isEmpty()) {
            throw new NotFoundException("Request with id=" + eventId + " was not found.");
        }
        Event updateEvent = event.get();
        ParticipationRequest requestConfirmed = request.get();
        if (!updateEvent.getRequestModeration() || updateEvent.getParticipantLimit().equals(0)) {
            return RequestMapper.fromRequestToDto(requestConfirmed);
        } else {
            if (updateEvent.getConfirmedRequests() < updateEvent.getParticipantLimit()) {
                requestConfirmed.setStatus(Status.CONFIRMED);
                updateEvent.setConfirmedRequests(updateEvent.getConfirmedRequests() + 1);
                eventRepository.save(updateEvent);
                if (updateEvent.getConfirmedRequests().equals(updateEvent.getParticipantLimit())) {
                    List<ParticipationRequest> requestsToCancel = requestRepository.findAllByEvent_Id(eventId);
                    requestsToCancel.stream().forEach(req -> req.setStatus(Status.REJECTED));
                    requestRepository.saveAll(requestsToCancel);
                }
                return RequestMapper.fromRequestToDto(requestConfirmed);
            } else {
                throw new AboveLimitException("Достигнут лимит участников");
            }
        }
    }

    @Override
    public ParticipationRequestDto rejectRequestByUser(Long userId, Long eventId, Long reqId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Optional<ParticipationRequest> request = requestRepository.findById(reqId);
        if (request.isEmpty()) {
            throw new NotFoundException("Request with id=" + eventId + " was not found.");
        }
        ParticipationRequest requestToReject = request.get();
        requestToReject.setStatus(Status.REJECTED);
        return RequestMapper.fromRequestToDto(requestRepository.save(requestToReject));
    }

    @Override
    public EventFullDto redactEventByAdmin(Long eventId, AdminUpdateEventRequest updateEvent) {
        Optional<Event> eventToRedact = eventRepository.findById(eventId);
        if (eventToRedact.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Event event = eventToRedact.get();
        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getCategory() != null) {
            Optional<Category> category = categoryRepository.findById(updateEvent.getCategory());
            if (category.isEmpty()) {
                throw new NotFoundException("Category with id=" + updateEvent.getCategory() + " was not found.");
            } else {
                event.setCategory(category.get());
            }
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), FORMAT));
        }
        if (updateEvent.getLocation() != null) {
            Location newLocation = locationService.update(updateEvent.getLocation(), event.getLocation().getId());
            event.setLocation(newLocation);
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        return EventMapper.fromEventToFull(eventRepository.save(event));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Optional<Event> eventToRedact = eventRepository.findById(eventId);
        if (eventToRedact.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Event event = eventToRedact.get();
        LocalDateTime publish = LocalDateTime.now();
        if (publish.isAfter(event.getEventDate().minusHours(1L))) {
            throw new WrongTimeException("До начала события меньше часа");
        }
        event.setPublishedOn(publish);
        if (!event.getState().equals(Status.PENDING)) {
            throw new WrongStatusException("Событие уже опубликовано");
        }
        event.setState(Status.PUBLISHED);
        return EventMapper.fromEventToFull(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Optional<Event> eventToRedact = eventRepository.findById(eventId);
        if (eventToRedact.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Event event = eventToRedact.get();
        if (event.getState().equals(Status.PUBLISHED)) {
            throw new WrongStatusException("Событие уже опубликовано");
        }
        event.setState(Status.CANCELED);
        return EventMapper.fromEventToFull(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> getEventsForAdmin(List<Long> users, List<String> states, List<Long> categories,
                                                String rangeStart, String rangeEnd, Integer from, Integer size) {
        LocalDateTime start = LocalDateTime.parse(rangeStart, FORMAT);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, FORMAT);
        List<Event> events = eventRepository.findForAdmin(users, states, categories, start, end,
                PageRequest.of(from / size, size)).toList();
        return events.stream().map(event -> EventMapper.fromEventToFull(event)).collect(Collectors.toList());
    }
}
