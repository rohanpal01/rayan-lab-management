package com.example.rayanlabmanagement.dto;

import com.example.rayanlabmanagement.entity.TestParameter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class TestDTO {


    private Long id;

    private String testName;
    private String category;
    private double price;
    private List<TestParameterDTO> parameters;
}
