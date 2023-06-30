package ru.nicetu.newsportal.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nicetu.newsportal.exception.custom.NewsNotFoundException;
import ru.nicetu.newsportal.exception.custom.ThemeNotFoundException;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.models.Theme;
import ru.nicetu.newsportal.repositories.NewsRepository;
import ru.nicetu.newsportal.repositories.ThemesRepository;
import ru.nicetu.newsportal.services.NewsService;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.disjoint;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ThemesRepository themesRepository;

    @Override
    public List<News> findAllNews(Person person) {
        List<News> newsList = newsRepository.findAllByOrderByDateDesc();
        newsList.removeIf(news -> !disjoint(news.getThemes(), person.getBannedThemes()));
        newsList.sort(Comparator.comparing(news -> getNumberOfDuplicatedThemes(news.getThemes(), person.getFavouriteThemes())));
        Collections.reverse(newsList);

        return newsList;
    }

    public Integer getNumberOfDuplicatedThemes(Set<Theme> newsThemes, Set<Theme> personThemes) {
        Integer num = 0;
        for (Theme theme : newsThemes) {
            if (personThemes.contains(theme)) num++;
        }

        return num;
    }

    @Override
    public List<News> findAllNews() {
        List<News> newsList = newsRepository.findAllByOrderByDateDesc();
        newsList.removeIf(news -> news.getDate().isBefore(LocalDateTime.now().minusDays(1)));
        return newsList;
    }

    @Override
    public News findById(int id) {
        Optional<News> newsById = newsRepository.findById(id);
        return newsById.orElseThrow(NewsNotFoundException::new);
    }

    @Override
    public News saveNews(News news) {
        news.setDate(LocalDateTime.now());
        return newsRepository.save(news);
//        return newsRepository.save(news).orElseThrow(NewsNotCreatedException::new);
    }

    @Override
    public void deleteById(int id) {
        Optional<News> newsById = newsRepository.findById(id);
        newsRepository.delete(newsById.orElseThrow(NewsNotFoundException::new));
    }

    @Override
    public News updateNews(int newsToBeUpdated_id, News newNews) {
        News newsToBeUpdated = findById(newsToBeUpdated_id);
        if (newNews.getHeader() != null) newsToBeUpdated.setHeader(newNews.getHeader());
        if (newNews.getText() != null) newsToBeUpdated.setText(newNews.getText());
        if (newNews.getPicture()!= null) newsToBeUpdated.setPicture(newNews.getPicture());
        if (newNews.getThemes() != null) {
            newsToBeUpdated.setThemes(new HashSet<>());
            updateThemes(newNews.getId(), newNews.getThemes());
        }
        return newsToBeUpdated;
    }

    @Override
    public Theme saveTheme(Theme theme) {
        return themesRepository.save(theme);
//        return themesRepository.save(theme).orElseThrow(ThemeNotCreatedException::new);
    }

    @Override
    public void addThemeToNews(int id, String theme) {
        News news = newsRepository.findById(id).orElseThrow(NewsNotFoundException::new);
        Theme newTheme = themesRepository.findByName(theme);
        if(newTheme == null){
            newTheme = saveTheme(new Theme(theme));
        }

        news.getThemes().add(newTheme);
    }

    @Override
    public void updateThemes(int id, Set<Theme> newThemes) {
        for (Theme theme : newThemes) {
            addThemeToNews(id, theme.getName());
        }
    }
}
