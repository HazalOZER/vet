package com.example.vet.dto.request.animalRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSaveRequest {

    private String name;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    @NotNull(message = "Hayvan Sahibi bilgisi gerekmektedir.")
   private long customerId;
}
