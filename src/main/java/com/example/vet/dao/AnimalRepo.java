package com.example.vet.dao;

import com.example.vet.entity.Animal;
import com.example.vet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
    List<Animal> findByName(String name);
    Animal findByNameAndBreedAndGenderAndColourAndDateOfBirthAndCustomer(String name, String breed, String gender, String colour, LocalDate dateOfBirth,Customer customer);
}
