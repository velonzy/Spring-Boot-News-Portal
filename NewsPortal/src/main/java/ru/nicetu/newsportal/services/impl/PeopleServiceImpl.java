package ru.nicetu.newsportal.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nicetu.newsportal.exception.custom.PersonAlreadyExistsException;
import ru.nicetu.newsportal.exception.custom.ThemeNotFoundException;
import ru.nicetu.newsportal.models.*;
import ru.nicetu.newsportal.repositories.PeopleRepository;
import ru.nicetu.newsportal.repositories.RolesRepository;
import ru.nicetu.newsportal.repositories.ThemesRepository;
import ru.nicetu.newsportal.services.PeopleService;

import java.util.ArrayList;
import java.util.Collection;

@Service @RequiredArgsConstructor
@Transactional
public class PeopleServiceImpl implements PeopleService, UserDetailsService {
    private final PeopleRepository peopleRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final ThemesRepository themesRepository;

    @Override
    public Person getPerson(String userName) {
        return peopleRepository.findByEmail(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = peopleRepository.findByEmail(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(person.getEmail(),
                person.getPassword(), authorities);
    }

    @Override
    public Person savePerson(Person person) {
        Person checkPerson = peopleRepository.findByEmail(person.getEmail());
        if (checkPerson != null) {
            throw new PersonAlreadyExistsException();
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return peopleRepository.save(person);
    }

    @Override
    public Role saveRole(Role role) {
        return rolesRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {

        Person person = peopleRepository.findByEmail(userName);
        Role role = rolesRepository.findByName(roleName);
        person.getRoles().add(role);
    }

    @Override
    public void addFavouriteThemes(int person_id, String themes) {
        if (!themes.isEmpty()) {
            String[] themeNames = themes.split(", ");
            for (String theme : themeNames) {
                addFavouriteTheme(person_id, theme);
            }
        }
    }


    @Override
    public void addFavouriteTheme(int person_id, String theme) {
        Theme selectedTheme = themesRepository.findByName(theme);

        if (selectedTheme == null) return;

        Person person = peopleRepository.findById(person_id);
        if (checkIfOneThemeAreInFavouriteAndBannedLists(person, selectedTheme)){
            person.getBannedThemes().remove(selectedTheme);
        }

        person.getFavouriteThemes().add(selectedTheme);
        System.out.println(person.getFavouriteThemes());
    }

    @Override
    public void addBannedThemes(int person_id, String themes) {
        if (!themes.isEmpty()) {
            String[] themeNames = themes.split(", ");
            for (String theme : themeNames) {
                addBannedTheme(person_id, theme);
            }
        }
    }

    @Override
    public void addBannedTheme(int person_id, String theme) {
        Theme selectedTheme = themesRepository.findByName(theme);

        if (selectedTheme == null) return;

        Person person = peopleRepository.findById(person_id);
        if(checkIfOneThemeAreInFavouriteAndBannedLists(person, selectedTheme)) {
            person.getFavouriteThemes().remove(selectedTheme);
        }

        person.getBannedThemes().add(selectedTheme);
    }

    @Override
    public void deleteFavouriteThemes(int person_id, String themes) {
        if (!themes.isEmpty()) {
            String[] themeNames = themes.split(", ");
            for (String theme : themeNames) {
                deleteFavouriteTheme(person_id, theme);
            }
        }
    }

    @Override
    public void deleteFavouriteTheme(int person_id, String theme) {
        Theme selectedTheme = themesRepository.findByName(theme);

        Person person = peopleRepository.findById(person_id);
        person.getFavouriteThemes().remove(selectedTheme);
    }

    @Override
    public void deleteBannedThemes(int person_id, String themes) {
        if (!themes.isEmpty()) {
            String[] themeNames = themes.split(", ");
            for (String theme : themeNames) {
                deleteBannedTheme(person_id, theme);
            }
        }
    }

    @Override
    public void deleteBannedTheme(int person_id, String theme) {
        Theme selectedTheme = themesRepository.findByName(theme);

        Person person = peopleRepository.findById(person_id);
        person.getBannedThemes().remove(selectedTheme);
    }

    @Override
    public Boolean checkIfOneThemeAreInFavouriteAndBannedLists(Person person, Theme theme) {
        return person.getFavouriteThemes().contains(theme) || person.getBannedThemes().contains(theme);
    }
}
