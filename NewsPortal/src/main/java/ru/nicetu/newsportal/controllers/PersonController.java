package ru.nicetu.newsportal.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nicetu.newsportal.dto.PersonRegistrationDTO;
import ru.nicetu.newsportal.dto.PersonThemesDTO;
import ru.nicetu.newsportal.exception.ApiRequestException;
import ru.nicetu.newsportal.exception.custom.PersonAlreadyExistsException;
import ru.nicetu.newsportal.exception.custom.PersonNotFoundException;
import ru.nicetu.newsportal.exception.custom.ThemeNotFoundException;
import ru.nicetu.newsportal.models.Person;
import ru.nicetu.newsportal.services.PeopleService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
    public ResponseEntity<?> savePerson(@RequestBody @Valid PersonRegistrationDTO personRegistrationDTO) throws UsernameNotFoundException {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
            return ResponseEntity.created(uri).body(peopleService.savePerson(convertToPerson(personRegistrationDTO)));
        } catch (PersonAlreadyExistsException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> showOnePerson(@PathVariable("id") int id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            Person person = peopleService.getPerson(username);

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Person with such ID wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/settings")
    public ResponseEntity<?> changeAllPersonThemes(@RequestBody PersonThemesDTO personThemesDTO) {
        try {
            return new ResponseEntity<>(changeThemes(personThemesDTO), HttpStatus.OK);
        } catch (ThemeNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        }

    }

    private Person changeThemes(PersonThemesDTO personThemesDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Person person = peopleService.getPerson(username);

        peopleService.addFavouriteThemes(person.getId(), personThemesDTO.getFavouriteThemesToBeAdded());
        peopleService.addBannedThemes(person.getId(), personThemesDTO.getBannedThemesToBeAdded());
        peopleService.deleteFavouriteThemes(person.getId(), personThemesDTO.getFavouriteThemesToBeDeleted());
        peopleService.deleteBannedThemes(person.getId(), personThemesDTO.getBannedThemesToBeDeleted());

        return person;
    }

    private Person convertToPerson(PersonRegistrationDTO personRegistrationDTO) {
        System.out.println(personRegistrationDTO);
        Person person = modelMapper.map(personRegistrationDTO, Person.class);
        System.out.println(person);
        return person;
    }

}
