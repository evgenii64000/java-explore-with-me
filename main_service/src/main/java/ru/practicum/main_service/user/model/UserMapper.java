package ru.practicum.main_service.user.model;

public class UserMapper {

    public static User fromNewToUser(NewUserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .rating(0L)
                .build();
    }

    public static UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .rating(user.getRating())
                .build();
    }

    public static UserShortDto fromUserToShort(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .rating(user.getRating())
                .build();
    }
}
