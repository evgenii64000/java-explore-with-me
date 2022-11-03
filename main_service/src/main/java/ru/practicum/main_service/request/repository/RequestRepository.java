package ru.practicum.main_service.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.request.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequester(Long id);

    Optional<ParticipationRequest> findAllByRequesterAndEvent(Long userId, Long eventId);

    List<ParticipationRequest> findAllByEvent(Long eventId);
}
