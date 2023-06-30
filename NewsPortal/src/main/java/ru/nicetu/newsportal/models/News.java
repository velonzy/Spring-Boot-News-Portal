package ru.nicetu.newsportal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "header")
    @NotEmpty(message = "Заголовок не может быть пустым")
    @Size(max = 255, message = "Размер заголовка не должен превышать 255 символов")
    private String header;

    //чтобы не был пустым
    @Column(name = "text")
    @NotEmpty(message = "Текст не может быть пустым")
    private String text;

    @Column(name = "data")
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    private LocalDateTime date;

    @Column(name = "picture")
    private String picture;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "news")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "news")
    private List<Like> likes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Theme> themes;

    public News(String header, String text, String picture){
        this.header = header;
        this.text = text;
        this.picture = picture;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.themes = new HashSet<>();
    }
}
