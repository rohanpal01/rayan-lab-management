package com.example.rayanlabmanagement.dto;

import lombok.Data;

@Data
public class InvoiceDTO {
    private Long patientId;
    private Long testId;
    private Double amount;
}
