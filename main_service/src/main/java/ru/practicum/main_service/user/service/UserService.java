package ru.practicum.main_service.user.service;

import ru.practicum.main_service.user.model.NewUserRequest;
import ru.practicum.main_service.user.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest userRequest);

    List<UserDto> findUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);
}
