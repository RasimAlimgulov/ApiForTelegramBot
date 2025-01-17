package com.rasimalimgulov.reportapi.security;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;

}