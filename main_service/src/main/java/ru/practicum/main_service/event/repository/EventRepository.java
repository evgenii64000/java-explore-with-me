package ru.practicum.main_service.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.main_service.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', ?1, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', ?1, '%'))) " +
            "AND events.paid = ?3 AND events.category_id IN ?2 AND events.confirmed_requests < events.participant_limit " +
            "AND events.event_date BETWEEN ?4 AND ?5", nativeQuery = true)
    Page<Event> findEventsAvailableRange(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', ?1, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', ?1, '%'))) " +
            "AND events.paid = ?3 AND events.category_id IN ?2 AND events.confirmed_requests < events.participant_limit " +
            "AND events.event_date > ?4", nativeQuery = true)
    Page<Event> findEventsAvailable(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', ?1, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', ?1, '%'))) " +
            "AND events.paid = ?3 AND events.category_id IN ?2 AND events.event_date BETWEEN ?4 AND ?5 ", nativeQuery = true)
    Page<Event> findEventsRange(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', ?1, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', ?1, '%'))) " +
            "AND events.paid = ?3 AND events.category_id IN ?2 AND events.event_date > ?4 " +
            "ORDER BY events.?5", nativeQuery = true)
    Page<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, Pageable pageable);

    Page<Event> findAllByInitiator_Id(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE events.initiator_id IN ?1 AND events.state IN ?2 AND events.category_id IN ?3 " +
            "AND events.event_date BETWEEN ?4 AND ?5 ", nativeQuery = true)
    Page<Event> findForAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);
}
