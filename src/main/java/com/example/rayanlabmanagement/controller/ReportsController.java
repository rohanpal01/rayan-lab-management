/*
package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.dto.ReportDTO;
import com.example.rayanlabmanagement.dto.ResultDTO;
import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.entity.Report;
import com.example.rayanlabmanagement.entity.Result;
import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.repository.PatientRepository;
import com.example.rayanlabmanagement.repository.ReportRepository;
import com.example.rayanlabmanagement.repository.ResultRepository;
import com.example.rayanlabmanagement.repository.TestRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ResultRepository reportRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private TestRepository testRepo;
    @Autowired
    private ReportRepository reportRepository;
*/
/*
    @PostMapping
    public Report createReport(@RequestBody ReportDTO dto) {
        Patient patient = patientRepo.findById(dto.getPatientId()).orElseThrow();

        Report report = new Report();
        report.setPatient(patient);
        report.setCollectedDate(LocalDateTime.now());
        report.setReceivedDate(LocalDateTime.now());
        report.setReportDate(LocalDateTime.now());

        List<Result> results = dto.getResults().stream().map(r -> {
            Result tr = new Result();
            Test testReport = testRepo.findByTestName(r.getTestName());
            tr.setTest(testReport);
            tr.setResultRange(r.getResultRange());
            tr.setUnit(r.getUnit());
            tr.setNormalRange(r.getNormalRange());
            tr.setRemark(r.getRemark());
            tr.setReport(report);
            return tr;
        }).collect(Collectors.toList());

        report.setValues(results);
        return reportRepository.save(report);
    }

    @GetMapping("/{id}/pdf")
    public void generateReportPdf(@PathVariable Long id, HttpServletResponse response) throws Exception {

        Report report = reportRepository.findById(id).orElseThrow();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Header
        document.add(new Paragraph("SWATI DIAGNOSTIC CENTER"));
        document.add(new Paragraph("Patient: " + report.getPatient().getName() +
                " (" + report.getPatient().getAge() + "/" + report.getPatient().getGender() + ")"));
        document.add(new Paragraph("Ref By: " + report.getPatient().getExaminedBy()));
        document.add(new Paragraph("Collected: " + report.getCollectedDate()));
        document.add(new Paragraph("Reported: " + report.getReportDate()));
        document.add(new Paragraph("\n"));

        // Table of results
        PdfPTable table = new PdfPTable(5);
        table.addCell("Test");
        table.addCell("Observed Value");
        table.addCell("Unit");
        table.addCell("Reference Range");
        table.addCell("Remarks");

        for (Result tr : report.getValues()) {
            table.addCell(tr.getTest().getTestName());
            table.addCell(tr.getResultRange());
            table.addCell(tr.getUnit());
            table.addCell(tr.getNormalRange());
            table.addCell(tr.getRemark() != null ? tr.getRemark() : "");
        }

        document.add(table);

        // Footer
        document.add(new Paragraph("\nNote: This report is to help clinic for better patient management. Not valid for medico legal purpose."));
        document.close();
    }*//*

}
*/
