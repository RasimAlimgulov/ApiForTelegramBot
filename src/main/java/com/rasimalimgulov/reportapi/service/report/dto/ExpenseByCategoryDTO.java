package com.rasimalimgulov.reportapi.service.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseByCategoryDTO {
    private String categoryName;
    private Double totalExpense;
}

