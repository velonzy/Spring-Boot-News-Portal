package ru.nicetu.newsportal.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nicetu.newsportal.exception.custom.CommentNotFoundException;
import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.repositories.CommentsRepository;
import ru.nicetu.newsportal.services.CommentsService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    @Override
    public Comment findById(int id) {
        Comment commentById = commentsRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        return commentById;
    }

    @Override
    public Comment saveComment(Comment comment, int news_id, int person_id) {
        comment.setDate(LocalDateTime.now());
        comment.setNews(news_id);
        comment.setPerson(person_id);

        return commentsRepository.save(comment);
//        return commentsRepository.save(comment).orElseThrow(CommentNotCreatedException::new);
    }

    @Override
    public void deleteById(int id) {
        Optional<Comment> commentById = commentsRepository.findById(id);
        commentsRepository.delete(commentById.get());
    }

}
