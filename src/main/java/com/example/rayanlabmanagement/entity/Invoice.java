package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne private Patient patient;
    @ManyToOne private Test test;
    private Double amount;
    private String status; // unpaid, paid
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters
}
