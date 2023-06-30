package ru.nicetu.newsportal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.models.Like;
import ru.nicetu.newsportal.models.Theme;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
// DTO для отображения одной новости
public class NewsShowDTO {

    @NotEmpty(message = "Заголовок не может быть пустым")
    @Size(max = 255, message = "Размер заголовка не должен превышать 255 символов")
    private String header;
    @NotEmpty(message = "Текст не может быть пустым")
    private String text;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    private LocalDateTime date;
    private String picture;
    private Integer numberOfComments;
    private List<Comment> comments;
    private Integer numberOfLikes;
    private List<Like> likes;
    private Set<Theme> themes;
}
