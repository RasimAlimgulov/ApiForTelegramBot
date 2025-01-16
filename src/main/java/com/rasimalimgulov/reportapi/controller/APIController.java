package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.dto.AuthRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class APIController {
    @PostMapping("/authentication")
    public Boolean authentication(@RequestBody AuthRequest authRequest) {
        log.info("Приняли запрос на Аутентификацию: " + authRequest);
        return true;
    }
}
