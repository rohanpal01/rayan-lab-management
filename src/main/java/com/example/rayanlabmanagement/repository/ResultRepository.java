package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.Report;
import com.example.rayanlabmanagement.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByPatientId(String patientId);

    @Query("SELECT r FROM Result r " +
            "LEFT JOIN FETCH r.parameters p " +
            "LEFT JOIN FETCH p.parameter " +
            "LEFT JOIN FETCH r.patient " +
            "LEFT JOIN FETCH r.test")
    List<Result> findAllWithDetails();
}

