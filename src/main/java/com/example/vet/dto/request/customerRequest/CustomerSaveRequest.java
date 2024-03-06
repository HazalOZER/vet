package com.example.vet.dto.request.customerRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveRequest {

    @NotNull(message = "İsim boş olamaz")
    private String name;
    private String phone;
    @Email
    private String mail;
    private String address;
    private String city;

}
