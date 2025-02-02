package com.rasimalimgulov.reportapi.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpCatRequest {
   private String nameExpenseCategory;
   private String username;
}
