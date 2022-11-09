package ru.practicum.main_service.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main_service.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users " +
            "WHERE users.id IN :ids ", nativeQuery = true)
    Page<User> findByIds(List<Long> ids, Pageable pageable);
}
