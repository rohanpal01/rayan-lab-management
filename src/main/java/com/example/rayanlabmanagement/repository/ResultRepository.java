package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByPatientId(String patientId);
}

