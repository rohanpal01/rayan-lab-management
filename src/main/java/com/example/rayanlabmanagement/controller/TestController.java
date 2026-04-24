package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private TestRepository testRepo;

    @PostMapping
    public Test addTest(@RequestBody Test test) {
        return testRepo.save(test);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testRepo.findAll();
    }
}
