package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.requests.IncomeRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ReportController {
    @PostMapping("/income")
    public boolean income(@RequestBody IncomeRequest incomeRequest){
        log.info(incomeRequest);
        return true;
    }
}
