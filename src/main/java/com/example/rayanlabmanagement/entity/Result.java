package com.example.rayanlabmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne private Test test;
    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResultParameter> parameters;
    private String remark;
    private String status; // pending, verified
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
