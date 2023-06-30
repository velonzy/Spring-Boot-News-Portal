package ru.nicetu.newsportal.controllers;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nicetu.newsportal.dto.CommentDTO;
import ru.nicetu.newsportal.dto.NewsDTO;
import ru.nicetu.newsportal.dto.NewsShowDTO;
import ru.nicetu.newsportal.dto.NewsUpdateDTO;
import ru.nicetu.newsportal.exception.ApiRequestException;
import ru.nicetu.newsportal.exception.custom.*;
import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.services.*;
import ru.nicetu.newsportal.services.impl.NewsServiceImpl;
import ru.nicetu.newsportal.services.impl.PeopleServiceImpl;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsServiceImpl newsService;

    private final LikesService likesService;

    private final PeopleServiceImpl peopleService;

    private final CommentsService commentsService;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<NewsDTO>> getNews(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Person person = peopleService.getPerson(username);

        if (person != null) {
            return ResponseEntity.ok().body(newsService.findAllNews(person).stream().map(this::convertToNewsDTO)
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok().body(newsService.findAllNews().stream().map(this::convertToNewsDTO)
                    .collect(Collectors.toList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showOneNews(@PathVariable("id") int id){
        try{
            News news = newsService.findById(id);
            return new ResponseEntity<>(convertToNewsShowDTO(news), HttpStatus.OK);
        } catch (NewsNotFoundException e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createNews(@RequestBody NewsUpdateDTO newsUpdateDTO){
        return ResponseEntity.ok().body(newsService
                .saveNews(convertToNewsToBeSaved(newsUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneNews(@PathVariable int id) {
        try {
            newsService.deleteById(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (NewsNotFoundException e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> createLike(@PathVariable("id") int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            Person person = peopleService.getPerson(username);
            News news = newsService.findById(id);

            likesService.addLikeToNews(person, news);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            Person person = peopleService.getPerson(username);
            News news = newsService.findById(id);

            likesService.deleteLikeToNews(person, news);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getNewsToEdit(@PathVariable("id") int id){
        try{
            News news = newsService.findById(id);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (NewsNotFoundException e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editNews(@PathVariable("id") int id, @RequestBody NewsUpdateDTO newsUpdateDTO) {
        try {
            News news = newsService.updateNews(id, convertToNews(newsUpdateDTO));
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (NewsNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createComment(@PathVariable("id") int id, @RequestBody CommentDTO commentDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            Person person = peopleService.getPerson(username);

            Comment newComment = commentsService.saveComment(convertToComment(commentDTO), id, person.getId());
            return new ResponseEntity<>(newComment, HttpStatus.OK);
        } catch (NewsNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @GetMapping("/{id}/{comment_id}")
    public ResponseEntity<?> getComment(@PathVariable("id") int id, @PathVariable("comment_id") int comment_id) {
        try {
            Comment existingComment = commentsService.findById(comment_id);

            return new ResponseEntity<>(existingComment, HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int id, @PathVariable("comment_id") int comment_id) {
        try {
            commentsService.deleteById(comment_id);
            return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    private News convertToNewsToBeSaved(NewsUpdateDTO newsUpdateDTO) {
        return modelMapper.map(newsUpdateDTO, News.class);
    }

    private News convertToNews(NewsUpdateDTO newsUpdateDTO) {
        return modelMapper.map(newsUpdateDTO, News.class);
    }

    private NewsShowDTO convertToNewsShowDTO(News news) {
        NewsShowDTO newsShowDTO = modelMapper.map(news, NewsShowDTO.class);
        newsShowDTO.setNumberOfLikes(news.getLikes().size());
        newsShowDTO.setNumberOfComments(news.getComments().size());
        return newsShowDTO;
    }

    private NewsDTO convertToNewsDTO(News news) {
        NewsDTO newsDTO = modelMapper.map(news, NewsDTO.class);
        newsDTO.setNumberOfComments(news.getComments().size());
        newsDTO.setNumberOfLikes(news.getLikes().size());
        return newsDTO;
    }

    private Comment convertToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

}

