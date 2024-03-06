package com.example.vet.dto.request.animalRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    private long id;
    private String name;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;

    private long customerId;
}
