package com.example.vet.dto.request.doctorRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateRequest {

    private long id;
    private String name;
    private String phone;
    @Email
    private String mail;
    private String address;
    private String city;
}
