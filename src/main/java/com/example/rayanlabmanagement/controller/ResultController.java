package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.entity.Result;
import com.example.rayanlabmanagement.dto.ResultDTO;
import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.repository.PatientRepository;
import com.example.rayanlabmanagement.repository.ResultRepository;
import com.example.rayanlabmanagement.repository.TestRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultRepository resultRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private TestRepository testRepo;

    @PostMapping("/entry")
    public Result addResult(@RequestBody ResultDTO dto) {
        Patient patient = patientRepo.findByName(dto.getPatientName());

        Test test = testRepo.findByTestName(dto.getTestName());

        Result result = new Result();
        result.setPatient(patient);
        result.setTest(test);
        result.setNormalRange(dto.getNormalRange());
        result.setResultRange(dto.getResultRange());
        result.setRemark(dto.getRemark());
        result.setStatus("pending");
        return resultRepo.save(result);
    }

    @GetMapping("/view")
    public List<Result> getAllResults() {
        return resultRepo.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<Result> getResultsByPatient(@PathVariable String patientId) {
        return resultRepo.findByPatientId(patientId);
    }

    @PutMapping("/{id}/verify")
    public Result verifyResult(@PathVariable Long id) {
        Result result = resultRepo.findById(id).orElseThrow();
        result.setStatus("verified");
        return resultRepo.save(result);
    }

    @GetMapping("/{id}/report")
    public void generateReport(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Result result = resultRepo.findById(id).orElseThrow();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=result.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Patient Details: " + result.getPatient()));
        document.add(new Paragraph("Test Details: " + result.getTest()));
        document.add(new Paragraph("Normal Range: " + result.getNormalRange()));
        document.add(new Paragraph("Result Range: " + result.getResultRange()));
        document.add(new Paragraph("Remark: " + result.getRemark()));
        document.add(new Paragraph("Status: " + result.getStatus()));
        document.close();
    }

}

