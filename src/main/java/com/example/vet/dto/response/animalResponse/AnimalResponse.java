package com.example.vet.dto.response.animalResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private long id;
    private String name;
    private String breed;
    private String gender;
    private String colour;
    private int customerId;

}
