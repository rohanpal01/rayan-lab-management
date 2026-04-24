package com.example.rayanlabmanagement.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("dailyTests", 120);
        stats.put("weeklyRevenue", 50000);
        return stats;
    }
}
