package com.rasimalimgulov.reportapi.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewClientRequest {
    private String username;
    private String clientName;
    private String phone;
    private String serviceType;
}
