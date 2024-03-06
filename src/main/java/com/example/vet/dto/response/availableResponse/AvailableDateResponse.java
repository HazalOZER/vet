package com.example.vet.dto.response.availableResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private long id;
    private LocalDate availableDate;
    private int doctorId;
}
