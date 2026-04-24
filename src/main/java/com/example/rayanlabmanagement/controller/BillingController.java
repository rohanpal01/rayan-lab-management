package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Invoice;
import com.example.rayanlabmanagement.dto.InvoiceDTO;
import com.example.rayanlabmanagement.entity.Patient;
import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.repository.InvoiceRepository;
import com.example.rayanlabmanagement.repository.PatientRepository;
import com.example.rayanlabmanagement.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private TestRepository testRepo;

    @PostMapping("/invoice")
    public Invoice createInvoice(@RequestBody InvoiceDTO dto) {
        Patient patient = patientRepo.findById(dto.getPatientId()).orElseThrow();
        Test test = testRepo.findById(dto.getTestId()).orElseThrow();

        Invoice invoice = new Invoice();
        invoice.setPatient(patient);
        invoice.setTest(test);
        invoice.setAmount(dto.getAmount());
        invoice.setStatus("unpaid");
        return invoiceRepo.save(invoice);
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    @GetMapping("/invoices/{patientId}")
    public List<Invoice> getInvoicesByPatient(@PathVariable String patientId) {
        return invoiceRepo.findByPatientId(patientId);
    }

    @PutMapping("/invoice/{id}/pay")
    public Invoice markPaid(@PathVariable Long id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow();
        invoice.setStatus("paid");
        return invoiceRepo.save(invoice);
    }
}
