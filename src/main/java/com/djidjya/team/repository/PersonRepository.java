package com.djidjya.team.repository;

import com.djidjya.team.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM person")
    List<Person> findAll();
}
