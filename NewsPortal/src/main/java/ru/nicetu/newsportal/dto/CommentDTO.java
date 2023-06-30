package ru.nicetu.newsportal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @Size(max = 1000, message = "Текст не должен превышать 1000 символов")
    @NotEmpty(message = "Текст не может быть пустым")
    private String text;
}
