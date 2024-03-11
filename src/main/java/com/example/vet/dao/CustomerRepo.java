package com.example.vet.dao;

import com.example.vet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    List<Customer> findByName (String name);
    Customer findByPhoneAndMail(String phone, String mail);
}
