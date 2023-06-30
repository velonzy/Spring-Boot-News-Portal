package ru.nicetu.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nicetu.newsportal.models.Like;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Like, Integer> {

//    Optional<Like> save(Like like);

    Like findByPersonAndNews(Integer personId, Integer newsId);
}
