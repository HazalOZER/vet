package com.example.vet.dao;

import com.example.vet.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {
}
