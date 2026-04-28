package com.example.rayanlabmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestResultDTO {
    private Long testId;
    private List<ResultParamDTO> parameters;
}
