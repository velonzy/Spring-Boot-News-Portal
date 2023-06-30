package ru.nicetu.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nicetu.newsportal.models.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemesRepository extends JpaRepository<Theme, Integer> {
    Theme findByName(String name);
    List<Theme> findAll();
}
