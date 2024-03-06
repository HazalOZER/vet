package com.example.vet.dto.response.appointmentResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private long id;
    private LocalDateTime appointmentDate;
    private int animalId;
    private int doctorId;
}
