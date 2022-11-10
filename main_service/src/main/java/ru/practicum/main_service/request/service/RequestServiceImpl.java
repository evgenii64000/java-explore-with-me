package ru.practicum.main_service.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.Status;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.repository.EventRepository;
import ru.practicum.main_service.exceptions.*;
import ru.practicum.main_service.request.model.ParticipationRequest;
import ru.practicum.main_service.request.model.ParticipationRequestDto;
import ru.practicum.main_service.request.model.RequestMapper;
import ru.practicum.main_service.request.repository.RequestRepository;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository,
                       EventRepository eventRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        if (requests.isEmpty()) {
            throw new NotFoundException("Requests with id=" + userId + " was not found.");
        }
        return requests.stream().map(request -> RequestMapper.fromRequestToDto(request)).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        User requester = user.get();
        Optional<Event> eventToParticipate = eventRepository.findById(eventId);
        if (eventToParticipate.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Event event = eventToParticipate.get();
        if (event.getInitiator().equals(requester)) {
            throw new WrongIdException("Организатор не может участвовать в собственном событии");
        }
        if (!event.getState().equals(Status.PUBLISHED)) {
            throw new WrongStatusException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            throw new AboveLimitException("Достигнут лимит участников");
        }
        Optional<ParticipationRequest> possibleRequest = requestRepository.findAllByRequester_IdAndEvent_Id(userId, eventId);
        if (possibleRequest.isPresent()) {
            throw new AlreadyExistException("Запрос на участие уже создан");
        }
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .id(null)
                .created(LocalDateTime.now())
                .requester(requester)
                .event(event)
                .build();
        if (!event.getRequestModeration()) {
            participationRequest.setStatus(Status.CONFIRMED);
        } else {
            participationRequest.setStatus(Status.PENDING);
        }
        return RequestMapper.fromRequestToDto(requestRepository.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        Optional<ParticipationRequest> request = requestRepository.findById(requestId);
        if (request.isEmpty()) {
            throw new NotFoundException("Request with id=" + requestId + " was not found.");
        }
        ParticipationRequest requestToCancel = request.get();
        Event event = requestToCancel.getEvent();
        if (event.getParticipantLimit() != 0 && requestToCancel.getStatus().equals(Status.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        requestToCancel.setStatus(Status.CANCELED);
        return RequestMapper.fromRequestToDto(requestRepository.save(requestToCancel));
    }
}
