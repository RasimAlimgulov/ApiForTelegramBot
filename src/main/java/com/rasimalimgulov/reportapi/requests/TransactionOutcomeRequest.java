package com.rasimalimgulov.reportapi.requests;

import com.rasimalimgulov.reportapi.entity.MoneyType;
import com.rasimalimgulov.reportapi.entity.TransactionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionOutcomeRequest {

    private TransactionType type;

    private Double amount;

    private String expenseCategory;

    private String comment;

    private MoneyType moneyType;

    private String username;

}
