package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.entity.Sample;
import com.example.rayanlabmanagement.dto.SampleDTO;
import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.repository.PatientRepository;
import com.example.rayanlabmanagement.repository.SampleRepository;
import com.example.rayanlabmanagement.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/samples")
public class SampleController {
    @Autowired
    private SampleRepository sampleRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private TestRepository testRepo;


    @PostMapping
    public Sample addSample(@RequestBody SampleDTO dto) {
        Patient patient = patientRepo.findById(dto.getPatientId()).orElseThrow();
        Test test = testRepo.findById(dto.getTestId()).orElseThrow();

        Sample sample = new Sample();
        sample.setPatient(patient);
        sample.setTest(test);
        sample.setSampleType(dto.getSampleType());
        sample.setStatus("collected");
        return sampleRepo.save(sample);
    }

    @GetMapping
    public List<Sample> getAllSamples() {
        return sampleRepo.findAll();
    }

    @PutMapping("/{id}/status")
    public Sample updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Sample sample = sampleRepo.findById(id).orElseThrow();
        sample.setStatus(body.get("status"));
        return sampleRepo.save(sample);
    }
}

