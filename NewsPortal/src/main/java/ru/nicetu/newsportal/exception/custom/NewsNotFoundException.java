package ru.nicetu.newsportal.exception.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsNotFoundException extends RuntimeException {
    private String message = "Новость не найдена";

}
