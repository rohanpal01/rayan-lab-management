package com.example.rayanlabmanagement.dto;

import lombok.Data;

@Data
public class ResultDTO {
    private Long id;
    private String patientName;
    private String testName;
    private String normalRange;
    private String resultRange;
    private String remark;
    private String status;
}
