package ru.nicetu.newsportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nicetu.newsportal.models.Comment;
import ru.nicetu.newsportal.models.Theme;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
// DTO для создания новости и внесения изменений в нее
public class NewsUpdateDTO {

//    private int id;
    @Size(max = 255, message = "Размер заголовка не должен превышать 255 символов")
    private String header;
    @NotEmpty(message = "Текст не может быть пустым")
    private String text;
    private String picture;
    private Set<Theme> themes;
}
