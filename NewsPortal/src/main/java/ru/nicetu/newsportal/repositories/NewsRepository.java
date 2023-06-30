package ru.nicetu.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nicetu.newsportal.models.News;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    Optional<News> findById(Integer id);
//    Optional<News> save(News news);

    List<News> findAllByOrderByDateDesc();

    List<News> findAll();
}