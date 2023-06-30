package ru.nicetu.newsportal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private int person;

    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private int news;

    public Like(int person_id, int news_id){
        this.person = person_id;
        this.news = news_id;
    }
}
