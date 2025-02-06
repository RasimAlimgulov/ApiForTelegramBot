package com.rasimalimgulov.reportapi.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    String username;
    String password;
}
