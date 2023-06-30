package ru.nicetu.newsportal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 1000, message = "Текст не должен превышать 1000 символов")
    @NotEmpty(message = "Текст не может быть пустым")
    private String text;

    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    private LocalDateTime date;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private int person;
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private int news;

    public Comment(String text, int person, int news){
        this.text = text;
        this.person = person;
        this.news = news;
    }

    public Comment(String text){
        this.text = text;
    }
}
