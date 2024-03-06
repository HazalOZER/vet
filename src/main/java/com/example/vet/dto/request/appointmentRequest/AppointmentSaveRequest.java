package com.example.vet.dto.request.appointmentRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaveRequest {

    @NotNull(message = "Tarih bilgisi gerekmektedir")
    private LocalDateTime appointmentDate;
    @NotNull(message = "Doktor bilgisi gerekmektedir")
    private long doctorId;
    @NotNull(message = "Evcil hayvan bilgisi gerekmektedir")
    private long animalId;
}
