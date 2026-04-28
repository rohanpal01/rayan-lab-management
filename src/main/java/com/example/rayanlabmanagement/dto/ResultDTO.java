package com.example.rayanlabmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    private String patientName;
    private List<TestResultDTO> tests;
    private String remark;
}
