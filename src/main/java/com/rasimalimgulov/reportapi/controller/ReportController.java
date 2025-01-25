package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.requests.IncomeRequest;
import com.rasimalimgulov.reportapi.service.ClientService;
import com.rasimalimgulov.reportapi.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
public class ReportController {
    private final UserService userService;
    private final ClientService clientService;

    public ReportController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @PostMapping("/income")
    public boolean income(@RequestBody IncomeRequest incomeRequest){
        log.info(incomeRequest);
        return true;
    }
    @GetMapping("/clients")
    public List<Client> getClients(@RequestParam String username) {
        Optional<User> user = userService.findByUserName(username);
        log.info("Получили юзера при попытки получить список Клиентов"+user);
        if (user.isEmpty()) {
            return new ArrayList<>();
        }
        log.info("Сработал метод /clients в контроллере");
        return clientService.getAllClientsByUser(user.get());
    }

}
