package ru.practicum.main_service.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.request.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequester_Id(Long id);

    Optional<ParticipationRequest> findAllByRequester_IdAndEvent_Id(Long userId, Long eventId);

    List<ParticipationRequest> findAllByEvent_Id(Long eventId);
}
