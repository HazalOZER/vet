package com.example.vet.dao;

import com.example.vet.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    //List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate finishDate);

}
