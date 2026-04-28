package com.example.rayanlabmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_parameter")
public class TestParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param_id")
    private Long paramId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "ref_min")
    private String refMin;

    @Column(name = "ref_max")
    private String refMax;

    @Column(name = "method")
    private String method;

    // Getters & Setters
}