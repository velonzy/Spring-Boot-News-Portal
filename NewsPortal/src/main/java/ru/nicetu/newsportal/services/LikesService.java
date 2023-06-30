package ru.nicetu.newsportal.services;

import ru.nicetu.newsportal.models.Like;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;

public interface LikesService {
    void addLikeToNews(Person person, News news);
    void deleteLikeToNews(Person person, News news);
}
