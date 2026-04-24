package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private String contact;
    private String address;
    private String examinedBy;
    private String uniquePatientId = UUID.randomUUID().toString();

    // getters and setters
}
