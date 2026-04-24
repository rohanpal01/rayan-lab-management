package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientId(String patientId);
}
