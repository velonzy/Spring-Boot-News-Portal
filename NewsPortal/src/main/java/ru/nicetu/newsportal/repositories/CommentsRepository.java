package ru.nicetu.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findById(Integer id);
//    Optional<Comment> save(Comment comment);
    List<Comment> findAllByOrderByDateDesc();
    List<Comment> findAllByPersonAndNews(Integer personId, Integer newsId);
}