package com.example.vet.dto.request.appointmentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateRequest {
    private long id;
    private LocalDateTime appointmentDate;
    private long doctorId;
    private long animalId;
}
