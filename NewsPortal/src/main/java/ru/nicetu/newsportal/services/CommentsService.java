package ru.nicetu.newsportal.services;

import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;

import java.util.List;
import java.util.Optional;

public interface CommentsService {

    Comment findById(int id);

    Comment saveComment(Comment comment, int news_id, int person_id);

    void deleteById(int id);
}
