package ru.nicetu.newsportal.exception.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonNotFoundException extends RuntimeException {
    private String message = "Пользователь не найден";
}
