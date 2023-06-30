package ru.nicetu.newsportal.services.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.models.Role;
import ru.nicetu.newsportal.models.Theme;
import ru.nicetu.newsportal.repositories.PeopleRepository;
import ru.nicetu.newsportal.repositories.RolesRepository;
import ru.nicetu.newsportal.repositories.ThemesRepository;
import ru.nicetu.newsportal.services.PeopleService;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class PeopleServiceImplTest {

    @MockBean
    private PeopleRepository peopleRepository;
    @MockBean
    private RolesRepository rolesRepository;
    @MockBean
    private ThemesRepository themesRepository;
    @Autowired
    private PeopleService peopleService;

    @Test
    void getPerson() {
        Person person = new Person();
        person.setEmail("email@mail.ru");
        person.setPassword("1234");

        Mockito.doReturn(new Person())
                .when(peopleRepository)
                .findByEmail("email@mail.ru");
        assertThat(peopleService.getPerson("email@mail.ru"), instanceOf(Person.class));
        Mockito.verify(peopleRepository, Mockito.times(1)).findByEmail("email@mail.ru");
    }

    @Test
    void savePerson() {
        Person person = new Person();
        person.setEmail("new@mail.ru");
        person.setPassword("1234");

        Mockito.doReturn(new Person())
                .when(peopleRepository)
                .save(person);

        assertThat(peopleService.savePerson(person), instanceOf(Person.class));
        Mockito.verify(peopleRepository, Mockito.times(1)).save(person);
    }

    @Test
    void saveRole() {
        Role role = new Role();
        role.setName("USER");

        Mockito.doReturn(new Role())
                .when(rolesRepository)
                .save(role);

        assertThat(peopleService.saveRole(role), instanceOf(Role.class));
        Mockito.verify(rolesRepository, Mockito.times(1)).save(role);
    }

    @Test
    void addRoleToUser() {
        Role role = new Role();
        role.setName("USER");

        Person person = new Person();
        person.setEmail("new@mail.ru");
        person.setPassword("1234");

        Mockito.doReturn(new Role())
                .when(rolesRepository)
                .save(role);

        Mockito.doReturn(new Person())
                .when(peopleRepository)
                .save(person);

        assertThat(peopleService.savePerson(person), instanceOf(Person.class));
        assertThat(peopleService.saveRole(role), instanceOf(Role.class));
    }
}