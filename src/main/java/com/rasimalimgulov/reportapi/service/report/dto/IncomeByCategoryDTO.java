package com.rasimalimgulov.reportapi.service.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeByCategoryDTO {
    private String serviceType;
    private Double totalIncome;
}
