package com.example.rayanlabmanagement.dto;

import com.example.rayanlabmanagement.entity.Test;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class TestParameterDTO {

    private Long paramId;
    private String paramName;
    private String unit;
    private String refMin;
    private String refMax;
    private String method;
}
