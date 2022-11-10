package ru.practicum.main_service.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.user.model.NewUserRequest;
import ru.practicum.main_service.user.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest userRequest);

    List<UserDto> findUsers(List<Long> ids, Pageable pageable);

    void deleteUser(Long userId);
}
