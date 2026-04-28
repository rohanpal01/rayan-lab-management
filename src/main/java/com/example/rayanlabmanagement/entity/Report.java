package com.example.rayanlabmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Report {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

        @ManyToOne
        private Patient patient;

        private String examinedBy;

        private LocalDateTime createdAt;

        private String remark;

        @JsonManagedReference
        @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
        private Set<Result> results;

    // Getters & Setters
}