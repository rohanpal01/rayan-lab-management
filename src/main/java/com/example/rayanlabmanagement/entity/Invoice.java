package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Patient patient;
    @ManyToOne private Test test;
    private Double amount;
    private String status; // unpaid, paid
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters
}
