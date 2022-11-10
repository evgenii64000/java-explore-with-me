package ru.practicum.main_service.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.Like;
import ru.practicum.main_service.event.model.LikeDto;
import ru.practicum.main_service.event.model.LikeMapper;
import ru.practicum.main_service.event.repository.LikeRepository;
import ru.practicum.main_service.user.model.User;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public LikeDto addLike(User user, Event event, Boolean rate) {
        Like like = likeRepository.findAllByUser_IdAndEvent_id(user.getId(), event.getId());
        if (like != null) {
            like.setRate(rate);
        } else {
            like = Like.builder()
                    .user(user)
                    .event(event)
                    .rate(rate)
                    .build();
        }
        return LikeMapper.toDto(likeRepository.save(like));
    }

    public Boolean checkRate(Long userId, Long eventId, Boolean rate) {
        Like like = likeRepository.findAllByUser_IdAndEvent_id(userId, eventId);
        if (like == null) {
            return true;
        } else return like.getRate() != rate;
    }
}
