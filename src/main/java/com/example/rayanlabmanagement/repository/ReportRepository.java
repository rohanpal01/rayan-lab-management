package com.example.rayanlabmanagement.repository;


import com.example.rayanlabmanagement.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    /*@Query("SELECT r FROM Report r " +
            "LEFT JOIN FETCH r.patient " +
            "LEFT JOIN FETCH r.results res " +
            "LEFT JOIN FETCH res.test " +
            "LEFT JOIN FETCH res.parameters p " +
            "LEFT JOIN FETCH p.parameter")
    List<Report> findAllWithDetails();*/

    @Query("SELECT DISTINCT r FROM Report r " +
            "LEFT JOIN FETCH r.patient " +
            "LEFT JOIN FETCH r.results res " +
            "LEFT JOIN FETCH res.test " +
            "LEFT JOIN FETCH res.parameters p " +
            "LEFT JOIN FETCH p.parameter")
    List<Report> findAllWithDetails();

}

