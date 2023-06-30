package ru.nicetu.newsportal.services;

import ru.nicetu.newsportal.dto.NewsUpdateDTO;
import ru.nicetu.newsportal.models.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NewsService {
    List<News> findAllNews(Person person);
    List<News> findAllNews();

    News findById(int id);

    News saveNews(News news);

    void deleteById(int id);
    News updateNews(int newsToBeUpdated_id, News newNews);

    Theme saveTheme(Theme theme);

    void addThemeToNews(int id, String theme);

//    void deleteThemeToNews(int id, String theme);
//
//
//    List<Theme> findAllThemes();
    void updateThemes(int id, Set<Theme> newThemes);
}
