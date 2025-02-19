package com.example.vet.dao;

import com.example.vet.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    Doctor findByPhoneAndMail(String phone, String mail);
}
