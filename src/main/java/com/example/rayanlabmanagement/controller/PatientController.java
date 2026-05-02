package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientRepository repo;

    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        // Check by uniquePatientId if provided
        if (patient.getUniquePatientId() != null) {
            if (repo.findByUniquePatientId(patient.getUniquePatientId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Patient already exists"));
            }
        }

        // Fallback: check by name + contact (if contact provided)
        List<Patient> existingByName = repo.findByName(patient.getName());
        for(Patient patientData : existingByName) {
            if (patientData != null && patient.getContact() != null
                    && patient.getContact().equals(patientData.getContact())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Patient already exists"));
            }
        }

        Patient saved = repo.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return repo.findAll();
    }
}
