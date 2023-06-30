package ru.nicetu.newsportal.exception.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonAlreadyExistsException extends RuntimeException {
    private String message = "Пользователь с такой почтой уже существует";
}
