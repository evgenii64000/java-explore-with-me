package ru.practicum.stats_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats_service.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT * FROM hits " +
            "WHERE hits.timestamp BETWEEN ?1 AND ?2 AND hits.uri IN ?3 " +
            "GROUP BY hits.uri", nativeQuery = true)
    List<Hit> getHitsWithUriUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT * FROM hits " +
            "WHERE hits.timestamp BETWEEN ?1 AND ?2 AND hits.uri IN ?3 ", nativeQuery = true)
    List<Hit> getHitsWithUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT * FROM hits " +
            "WHERE hits.timestamp BETWEEN ?1 AND ?2 GROUP BY hits.uri", nativeQuery = true)
    List<Hit> getHitsUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM hits " +
            "WHERE hits.timestamp BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Hit> getHits(LocalDateTime start, LocalDateTime end);
}
