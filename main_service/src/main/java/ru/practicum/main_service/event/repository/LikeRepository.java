package ru.practicum.main_service.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.event.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findAllByUser_IdAndEvent_id(Long userId, Long eventId);
}
