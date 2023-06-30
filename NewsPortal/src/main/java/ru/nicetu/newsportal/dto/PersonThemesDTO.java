package ru.nicetu.newsportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nicetu.newsportal.models.Theme;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonThemesDTO {

    private String favouriteThemesToBeAdded = "";
    private String favouriteThemesToBeDeleted = "";
    private String bannedThemesToBeAdded = "";
    private String bannedThemesToBeDeleted = "";
}
