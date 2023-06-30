package ru.nicetu.newsportal.services;

import ru.nicetu.newsportal.models.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface PeopleService {

    Person savePerson(Person person);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    Person getPerson(String personName);

    void addFavouriteThemes(int person_id, String themes);
    void addFavouriteTheme(int person_id, String theme);
    void addBannedThemes(int person_id, String themes);

    void addBannedTheme(int person_id, String theme);
    void deleteFavouriteThemes(int person_id, String theme);

    void deleteFavouriteTheme(int person_id, String theme);
    void deleteBannedThemes(int person_id, String theme);
    void deleteBannedTheme(int person_id, String theme);

    Boolean checkIfOneThemeAreInFavouriteAndBannedLists(Person person, Theme theme);

}
