package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Patient patient;
    @ManyToOne private Test test;
    private String normalRange;
    private String resultRange;
    private String remark;
    private String status; // pending, verified
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters
}
