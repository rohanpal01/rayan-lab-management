package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUniquePatientId(String uniqueId);

    List<Patient> findByName(String name);
}
