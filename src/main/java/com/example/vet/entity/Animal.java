package com.example.vet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "gender")
    private String gender;
    @Column(name = "colour")
    private String colour;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
     List<Appointment> appointments;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.REMOVE)
    List<Vaccine> vaccines;


}
