package com.rasimalimgulov.reportapi.service.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeByClientDTO {
        private String clientName;
        private Double totalIncome;
}