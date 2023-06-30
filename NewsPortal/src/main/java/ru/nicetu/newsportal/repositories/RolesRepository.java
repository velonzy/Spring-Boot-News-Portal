package ru.nicetu.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nicetu.newsportal.models.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
