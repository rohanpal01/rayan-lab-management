package com.example.rayanlabmanagement.service;

import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }
    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public Patient addPatient(Patient patient) {
        //patient.setUniquePatientId(UUID.randomUUID().toString());
        return repo.save(patient);
    }
}
