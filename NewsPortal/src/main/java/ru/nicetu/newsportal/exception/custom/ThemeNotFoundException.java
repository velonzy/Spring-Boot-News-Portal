package ru.nicetu.newsportal.exception.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThemeNotFoundException extends RuntimeException {
    private String message = "Тема не найдена";
}
