package ru.practicum.main_service.request.model;

import ru.practicum.main_service.Status;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {

    public static ParticipationRequest fromDtoToRequest(ParticipationRequestDto participationRequestDto,
                                                        User requester, Event event) {
        return ParticipationRequest.builder()
                .id(participationRequestDto.getId())
                .status(Status.valueOf(participationRequestDto.getStatus()))
                .created(LocalDateTime.parse(participationRequestDto.getCreated(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .event(event)
                .requester(requester)
                .build();

    }

    public static ParticipationRequestDto fromRequestToDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(participationRequest.getStatus().toString())
                .requester(participationRequest.getRequester().getId())
                .event(participationRequest.getEvent().getId())
                .build();
    }
}
