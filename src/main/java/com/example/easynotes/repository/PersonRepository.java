package com.example.easynotes.repository;

import com.example.easynotes.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Find users by firstName
    List<Person> findByNameFirstName(String firstName);

    // Find users by lastName
    List<Person> findByNameLastName(String lastName);

    // Find users by country
    List<Person> findByAddressCountry(String country);
}
