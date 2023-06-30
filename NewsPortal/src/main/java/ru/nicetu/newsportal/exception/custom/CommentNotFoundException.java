package ru.nicetu.newsportal.exception.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentNotFoundException extends RuntimeException {
    private String message = "Комментарий не найден";
}
