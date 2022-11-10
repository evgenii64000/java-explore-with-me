package ru.practicum.main_service.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.exceptions.NoDataToUpdateException;
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
        if (userRequest.getName() == null || userRequest.getEmail() == null) {
            throw new NoDataToUpdateException("Отсутствуют данные для создания пользователя");
        }
        User user = userRepository.save(UserMapper.fromNewToUser(userRequest));
        return UserMapper.fromUserToDto(user);
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, Pageable pageable) {
        if (ids.isEmpty()) {
            return userRepository.findAll(pageable).stream()
                    .map(user -> UserMapper.fromUserToDto(user))
                    .collect(Collectors.toList());
        }
        return userRepository.findByIds(ids, pageable).stream()
                .map(user -> UserMapper.fromUserToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
