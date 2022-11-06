package ru.practicum.main_service.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.exceptions.NotFoundException;
import ru.practicum.main_service.user.model.NewUserRequest;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.model.UserDto;
import ru.practicum.main_service.user.model.UserMapper;
import ru.practicum.main_service.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User user = userRepository.save(UserMapper.fromNewToUser(userRequest));
        return UserMapper.fromUserToDto(user);
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, Integer from, Integer size) {
        List<User> users = userRepository.findByIds(ids, PageRequest.of(from / size, size)).toList();
        if (users.isEmpty()) {
            return userRepository.findAll(PageRequest.of(from / size, size)).stream()
                    .map(user -> UserMapper.fromUserToDto(user))
                    .collect(Collectors.toList());
        }
        return userRepository.findByIds(ids, PageRequest.of(from / size, size)).stream()
                .map(user -> UserMapper.fromUserToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
