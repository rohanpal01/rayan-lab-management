package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private String sampleType;
    private String description;
    private String schedule;
    private double price;

    // getters and setters
}
