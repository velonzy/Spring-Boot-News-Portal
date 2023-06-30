package ru.nicetu.newsportal.services.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nicetu.newsportal.models.News;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.repositories.NewsRepository;
import ru.nicetu.newsportal.repositories.ThemesRepository;
import ru.nicetu.newsportal.services.NewsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class NewsServiceImplTest {

    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @Test
    void findAllNews() {
        List<News> news = new ArrayList<>();
        News news1 = new News();
        news1.setDate(LocalDateTime.now());
        news.add(news1);

        News news2 = new News();
        news2.setDate(LocalDateTime.now());
        news.add(news2);

        Mockito.doReturn(news)
                .when(newsRepository)
                .findAllByOrderByDateDesc();

        List<News> checkNews = newsService.findAllNews();
        Assert.assertTrue(checkNews.contains(news1));
        Assert.assertTrue(checkNews.contains(news2));
    }

    @Test
    void findById() {
        int news_id = 0;

        Mockito.doReturn(new News())
                .when(newsRepository)
                .findById(news_id);

        assertThat(newsService.findById(news_id), instanceOf(News.class));
        Mockito.verify(newsRepository, Mockito.times(1)).findById(news_id);

    }

    @Test
    void saveNews() {
        News news = new News("header", "text", "picture1");
        news.setDate(LocalDateTime.now());

        Mockito.doReturn(Optional.of(new News()))
                .when(newsRepository)
                .save(news);

        assertThat(newsService.saveNews(news), instanceOf(News.class));
        Mockito.verify(newsRepository, Mockito.times(1)).save(news);
    }
}