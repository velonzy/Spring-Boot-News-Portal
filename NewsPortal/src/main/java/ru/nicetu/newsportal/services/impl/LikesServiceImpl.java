package ru.nicetu.newsportal.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nicetu.newsportal.models.Like;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.repositories.LikesRepository;
import ru.nicetu.newsportal.services.LikesService;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    public Like saveLike(Like like) {
        return likesRepository.save(like);
//        return likesRepository.save(like).orElseThrow(LikeNotCreatedException::new);
    }

    @Override
    public void addLikeToNews(Person person, News news) {
        Like like = likesRepository.findByPersonAndNews(person.getId(), news.getId());
        if (like == null) {
            like = saveLike(new Like(person.getId(), news.getId()));
            news.getLikes().add(like);
            person.getLikes().add(like);
        }
    }

    @Override
    public void deleteLikeToNews(Person person, News news) {
        Like like = likesRepository.findByPersonAndNews(person.getId(), news.getId());
        if (like != null) {
            likesRepository.delete(like);
        }
    }
}
