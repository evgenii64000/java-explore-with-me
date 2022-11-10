package ru.practicum.main_service.event.model;

public class LikeMapper {

    public static LikeDto toDto(Like like) {
        return LikeDto.builder()
                .user(like.getUser().getId())
                .event(like.getEvent().getId())
                .rate(like.getRate())
                .build();
    }
}
