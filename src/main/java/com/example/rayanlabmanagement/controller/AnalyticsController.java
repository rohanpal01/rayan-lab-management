package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Invoice;
import com.example.rayanlabmanagement.repository.InvoiceRepository;
import com.example.rayanlabmanagement.repository.ResultRepository;
import com.example.rayanlabmanagement.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private ResultRepository resultRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    // Daily test count
    @GetMapping("/tests/daily")
    public long getDailyTestCount() {
        LocalDate today = LocalDate.now();
        return testRepo.findAll().stream()
                .filter(t -> t.getSchedule().equalsIgnoreCase("Daily"))
                .count();
    }

    // Weekly test count
    @GetMapping("/tests/weekly")
    public long getWeeklyTestCount() {
        LocalDate weekStart = LocalDate.now().minusDays(7);
        return resultRepo.findAll().stream()
                .filter(r -> r.getCreatedAt().isAfter(weekStart.atStartOfDay()))
                .count();
    }

    // Revenue tracking
    @GetMapping("/revenue")
    public double getRevenue() {
        return invoiceRepo.findAll().stream()
                .filter(inv -> inv.getStatus().equalsIgnoreCase("paid"))
                .mapToDouble(Invoice::getAmount)
                .sum();
    }

    // Quality control: % verified results
    @GetMapping("/quality")
    public double getQualityControl() {
        long total = resultRepo.count();
        long verified = resultRepo.findAll().stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("verified"))
                .count();
        return total == 0 ? 0 : (verified * 100.0 / total);
    }
}
