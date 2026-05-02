package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.dto.TestDTO;
import com.example.rayanlabmanagement.entity.Test;
import com.example.rayanlabmanagement.entity.TestParameter;
import com.example.rayanlabmanagement.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private TestRepository testRepo;

    @PostMapping
    public Test addTest(@RequestBody TestDTO testRequest) {

        Test test = new Test();
        test.setTestName(testRequest.getTestName());
        test.setCategory(testRequest.getCategory());
        test.setPrice(testRequest.getPrice());

        // Convert parameters
        List<TestParameter> paramList = testRequest.getParameters().stream().map(p -> {
            TestParameter param = new TestParameter();
            param.setParamName(p.getParamName());
            param.setUnit(p.getUnit());
            param.setRefMin(p.getRefMin());
            param.setRefMax(p.getRefMax());
            param.setMethod(p.getMethod());
            param.setTest(test); // VERY IMPORTANT
            return param;
        }).toList();

        test.setParameters(paramList);

        return testRepo.save(test);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testRepo.findAll();
    }
}
