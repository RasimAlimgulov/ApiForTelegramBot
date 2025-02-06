package com.rasimalimgulov.reportapi.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeLoginRequest {
    String username;
    String newUsername;
}
