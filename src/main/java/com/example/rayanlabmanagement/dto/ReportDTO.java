package com.example.rayanlabmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ReportDTO {
    private Long patientId;
    private LocalDateTime collectedAt;
    private LocalDateTime receivedAt;
    private LocalDateTime reportedAt;
    private List<ResultDTO> results;
}
