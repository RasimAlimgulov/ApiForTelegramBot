package com.rasimalimgulov.reportapi.service.report.dto;

import com.rasimalimgulov.reportapi.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalReportDTO {
    private TransactionType transactionType;
    private Double totalAmount;
}

