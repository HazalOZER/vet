package com.example.vet.dao;

import com.example.vet.entity.Animal;
import com.example.vet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
   // @Query("FROM Animal AS a WHERE a.customer=:customer")
    List<Animal> findByName(String name);
}
