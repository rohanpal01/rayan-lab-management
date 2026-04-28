package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.dto.TestResultDTO;
import com.example.rayanlabmanagement.entity.*;
import com.example.rayanlabmanagement.dto.ResultDTO;
import com.example.rayanlabmanagement.reports.ReportService;
import com.example.rayanlabmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Content-Disposition")
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultRepository resultRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private TestParameterRepository testParameterRepo;

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private ReportService reportService;


    @GetMapping("/view")
    public List<Report> getAllReports() {
        return reportRepo.findAllWithDetails();
    }

    @GetMapping("/patient/{patientId}")
    public List<Result> getResultsByPatient(@PathVariable String patientId) {
        return resultRepo.findByPatientId(patientId);
    }

    @PutMapping("/{id}/verify")
    public Result verifyResult(@PathVariable Long id) {
        Result result = resultRepo.findById(id).orElseThrow();
        result.setStatus("Verified");
        return resultRepo.save(result);
    }


    @PostMapping("/entry")
    public Report saveReport(@RequestBody ResultDTO dto) {

        Patient patient = patientRepo.findByName(dto.getPatientName());

        Report report = new Report();
        report.setPatient(patient);
        report.setCreatedAt(LocalDateTime.now());
        report.setExaminedBy(patient.getExaminedBy());
        report.setRemark(dto.getRemark());

        Set<Result> results =  new HashSet<>();

        for (TestResultDTO testDto : dto.getTests()) {

            Test test = testRepo.findById(testDto.getTestId()).orElseThrow();

            Result result = new Result();
            result.setTest(test);
            result.setReport(report);
            result.setPatient(patient);
            result.setStatus("pending");

            List<ResultParameter> params = testDto.getParameters().stream().map(p -> {

                TestParameter tp = testParameterRepo.findById(p.getParamId()).orElseThrow();

                ResultParameter rp = new ResultParameter();
                rp.setParameter(tp);
                rp.setValue(p.getValue());
                rp.setResult(result);

                return rp;

            }).collect(Collectors.toList());
            System.out.println("Total params: " + params.size());
            result.setParameters(params);
            results.add(result);
        }

        report.setResults(results);

        return reportRepo.save(report);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id)  throws Exception  {


        byte[] pdf = reportService.generatePdf(id);
        Report report = reportRepo.findById(id).orElseThrow();
        String uniqueId = report.getPatient().getUniquePatientId();
        String date = LocalDate.now().toString();

        String fileName = uniqueId + "_" + date + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }

}

