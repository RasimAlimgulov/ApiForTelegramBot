package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.entity.*;
import com.rasimalimgulov.reportapi.requests.*;
import com.rasimalimgulov.reportapi.service.*;
import com.rasimalimgulov.reportapi.service.report.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final ExpenseCategoriesService expenseCategoriesService;
    private final ReportService reportService;

    public ReportController(UserService userService, ClientService clientService, ServiceTypeService serviceTypeService, TransactionService transactionService, ExpenseCategoriesService expenseCategoriesService, ReportService reportService) {
        this.userService = userService;
        this.clientService = clientService;
        this.serviceTypeService = serviceTypeService;
        this.transactionService = transactionService;
        this.expenseCategoriesService = expenseCategoriesService;
        this.reportService = reportService;
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
        log.info("addClient method: serviceType: " + newClientRequest.getServiceType() + ", client: " + client);
        ServiceType serviceType = serviceTypeService.findServiceTypeByNameAndUser(newClientRequest.getServiceType(), user.get());
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
    public Transaction addIncome(@RequestBody TransactionIncomeRequest incomeRequest) {
        Transaction transaction = transactionService.saveIncomeTransaction(incomeRequest);
        return transaction;
    }

    @PostMapping("/addoutcome")
    public Transaction addOutcome(@RequestBody TransactionOutcomeRequest outcomeRequest) {
        Transaction transaction = transactionService.saveOutcomeTransaction(outcomeRequest);
        return transaction;
    }

    @GetMapping("/expensecategories")
    public List<ExpenseCategory> getExpenseCategories(@RequestParam String username) {
        List<ExpenseCategory> expenseCategories = expenseCategoriesService.getAllExpenseCategoriesByUserId(username);
        return expenseCategories;
    }

    @PostMapping("/addexpensecategory")
    public ExpenseCategory addExpenseCategory(@RequestBody ExpCatRequest categoryRequest) {
        return expenseCategoriesService.addExpenseCategory(categoryRequest.getNameExpenseCategory(), categoryRequest.getUsername());
    }
//////////////////////////////////////////////////////////////////////////////////////////// Получение запросов на отчет EXCEL

    @GetMapping("/report/income-by-clients")
    public ResponseEntity<byte[]> downloadIncomeByClients(@RequestParam String username) {
        byte[] reportData = reportService.generateIncomeReportByClients(username);
        return buildExcelResponse(reportData, "income_by_clients.xlsx");
    }

    @GetMapping("/report/income-by-categories")
    public ResponseEntity<byte[]> downloadIncomeByCategories(@RequestParam String username) {
        byte[] reportData = reportService.generateIncomeReportByCategories(username);
        return buildExcelResponse(reportData, "income_by_categories.xlsx");
    }

    @GetMapping("/report/expenses-by-categories")
    public ResponseEntity<byte[]> downloadExpensesByCategories(@RequestParam String username) {
        byte[] reportData = reportService.generateExpenseReportByCategories(username);
        return buildExcelResponse(reportData, "expenses_by_categories.xlsx");
    }

    @GetMapping("/report/total")
    public ResponseEntity<byte[]> downloadTotalReport(@RequestParam String username) {
        byte[] reportData = reportService.generateTotalReport(username);
        return buildExcelResponse(reportData, "total_report.xlsx");
    }

    private ResponseEntity<byte[]> buildExcelResponse(byte[] data, String filename) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

}


