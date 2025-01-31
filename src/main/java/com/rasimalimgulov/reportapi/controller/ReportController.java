package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.ServiceType;
import com.rasimalimgulov.reportapi.entity.Transaction;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.requests.IncomeRequest;
import com.rasimalimgulov.reportapi.requests.NewClientRequest;
import com.rasimalimgulov.reportapi.requests.ServiceTypeRequest;
import com.rasimalimgulov.reportapi.requests.TransactionIncomeRequest;
import com.rasimalimgulov.reportapi.service.ClientService;
import com.rasimalimgulov.reportapi.service.ServiceTypeService;
import com.rasimalimgulov.reportapi.service.TransactionService;
import com.rasimalimgulov.reportapi.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
public class ReportController {
    private final UserService userService;
    private final ClientService clientService;
    private final ServiceTypeService serviceTypeService;
    private final TransactionService transactionService;
    public ReportController(UserService userService, ClientService clientService, ServiceTypeService serviceTypeService, TransactionService transactionService) {
        this.userService = userService;
        this.clientService = clientService;
        this.serviceTypeService = serviceTypeService;
        this.transactionService = transactionService;
    }

    @PostMapping("/income")
    public boolean income(@RequestBody IncomeRequest incomeRequest) {
        log.info(incomeRequest);
        return true;
    }

    @GetMapping("/clients")
    public List<Client> getClients(@RequestParam String username) {
        Optional<User> user = userService.findByUserName(username);
        log.info("Получили юзера при попытки получить список Клиентов" + user);
        if (user.isEmpty()) {
            return new ArrayList<>();
        }
        log.info("Сработал метод /clients в контроллере");
        return clientService.getAllClientsByUser(user.get());
    }

    @PostMapping("/addclient")
    private Client addClient(@RequestBody NewClientRequest newClientRequest) {
        Optional<User> user = userService.findByUserName(newClientRequest.getUsername());

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Client client = new Client();
        client.setUser(user.get());
        client.setFullName(newClientRequest.getClientName());
        client.setPhoneNumber(newClientRequest.getPhone());
        ServiceType serviceType = serviceTypeService.findServiceTypeByName(newClientRequest.getServiceType());
        if (serviceType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service type not found");
        }

        client.setServiceType(serviceType);

        return clientService.addClient(client);
    }

    @GetMapping("/servicetypes")
    public List<ServiceType> getServiceTypes(@RequestParam String username) {
        List<ServiceType> serviceTypes = serviceTypeService.getAllServiceTypes(username);
        if (serviceTypes.isEmpty()) {
            return new ArrayList<>();
        }
        return serviceTypes;
    }

    @PostMapping("/addservicetype")
    public ServiceType addServiceType(@RequestBody ServiceTypeRequest serviceTypeRequest) {
        ServiceType serviceType = serviceTypeService.addServiceType(serviceTypeRequest.getUsername(), serviceTypeRequest.getServiceTypeName());
        return serviceType;
    }

    @PostMapping("/addincome")
    public Transaction addIncome(@RequestBody TransactionIncomeRequest incomeRequest){
       Transaction transaction=transactionService.saveTransaction(incomeRequest);
       return transaction;
    }

}
