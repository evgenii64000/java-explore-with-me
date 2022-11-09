package ru.practicum.main_service.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main_service.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', :text, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', :text, '%'))) " +
            "AND events.paid = :paid AND events.category_id IN :categories AND events.confirmed_requests < events.participant_limit " +
            "AND events.event_date BETWEEN :rangeStart AND :rangeEnd", nativeQuery = true)
    Page<Event> findEventsAvailableRange(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', :text, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', :text, '%'))) " +
            "AND events.paid = :paid AND events.category_id IN :categories AND events.confirmed_requests < events.participant_limit " +
            "AND events.event_date > :rangeStart", nativeQuery = true)
    Page<Event> findEventsAvailable(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', :text, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', :text, '%'))) " +
            "AND events.paid = :paid AND events.category_id IN :categories AND events.event_date BETWEEN :rangeStart AND :rangeEnd ", nativeQuery = true)
    Page<Event> findEventsRange(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE (LOWER(events.annotation) like LOWER(CONCAT('%', :text, '%')) OR LOWER(events.description) like LOWER(CONCAT('%', :text, '%'))) " +
            "AND events.paid = :paid AND events.category_id IN :categories AND events.event_date > :rangeStart ", nativeQuery = true)
    Page<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, Pageable pageable);

    Page<Event> findAllByInitiator_Id(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE events.initiator_id IN :users AND events.state IN :states AND events.category_id IN :categories " +
            "AND events.event_date BETWEEN :rangeStart AND :rangeEnd ", nativeQuery = true)
    Page<Event> findForAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE events.state IN :states AND events.event_date BETWEEN :rangeStart AND :rangeEnd ", nativeQuery = true)
    Page<Event> findForAdminNullParam(List<String> states, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                      Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE events.initiator_id IN :users AND events.state IN :states " +
            "AND events.event_date BETWEEN :rangeStart AND :rangeEnd ", nativeQuery = true)
    Page<Event> findForAdminOnlyUsers(List<Long> users, List<String> states, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT * FROM events " +
            "WHERE events.state IN :states AND events.category_id IN :categories " +
            "AND events.event_date BETWEEN :rangeStart AND :rangeEnd ", nativeQuery = true)
    Page<Event> findForAdminOnlyCategories(List<String> states, List<Long> categories, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);
}
