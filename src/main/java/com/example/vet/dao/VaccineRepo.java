package com.example.vet.dao;

import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import com.example.vet.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {
List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate finishDate);
}
