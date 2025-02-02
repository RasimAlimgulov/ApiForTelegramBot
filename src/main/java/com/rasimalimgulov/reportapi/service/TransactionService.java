package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.ExpenseCategory;
import com.rasimalimgulov.reportapi.entity.Transaction;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.repository.ClientRepository;
import com.rasimalimgulov.reportapi.repository.ExpenseCategoriesRepository;
import com.rasimalimgulov.reportapi.repository.TransactionRepository;
import com.rasimalimgulov.reportapi.repository.UserRepository;
import com.rasimalimgulov.reportapi.requests.TransactionIncomeRequest;
import com.rasimalimgulov.reportapi.requests.TransactionOutcomeRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;
    public TransactionService(TransactionRepository transactionRepository, ClientRepository clientRepository, UserRepository userRepository, ExpenseCategoriesRepository expenseCategoriesRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
    }

    public Transaction saveIncomeTransaction(TransactionIncomeRequest incomeRequest) {
        // Проверяем существование клиента
        Client client = clientRepository.findById(incomeRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Клиент не найден: " + incomeRequest.getClientId()));

        // Проверяем существование пользователя
        User user = userRepository.findByUsername(incomeRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + incomeRequest.getUsername()));

        // Создаём транзакцию
        Transaction transaction = Transaction.builder()
                .type(incomeRequest.getType())
                .amount(incomeRequest.getAmount())
                .transactionDate(LocalDateTime.now())
                .category(null) // Если категории нет, оставляем null
                .client(client)
                .serviceType(client.getServiceType()) // Берем тип услуги из клиента
                .status(incomeRequest.getStatus())
                .comment(incomeRequest.getComment())
                .filePath(incomeRequest.getFilePath())
                .moneyType(incomeRequest.getMoneyType())
                .user(user)
                .build();
        return transactionRepository.save(transaction);
    }
    public Transaction saveOutcomeTransaction(TransactionOutcomeRequest outcomeRequest) {
        User user = userRepository.findByUsername(outcomeRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + outcomeRequest.getUsername()));

        ExpenseCategory expenseCategory=expenseCategoriesRepository.findByNameAndUser(outcomeRequest.getExpenseCategory(),user);

        // Создаём транзакцию
        Transaction transaction = Transaction.builder()
                .type(outcomeRequest.getType())
                .amount(outcomeRequest.getAmount())
                .transactionDate(LocalDateTime.now())
                .category(expenseCategory)
                .client(null)
                .serviceType(null)
                .status(null)
                .comment(outcomeRequest.getComment())
                .filePath(null)
                .moneyType(outcomeRequest.getMoneyType())
                .user(user)
                .build();
        return transactionRepository.save(transaction);
    }


}
