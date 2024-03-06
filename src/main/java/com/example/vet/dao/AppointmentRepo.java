package com.example.vet.dao;

import com.example.vet.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

}
