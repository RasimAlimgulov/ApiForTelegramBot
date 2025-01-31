package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.ExpenseCategory;
import com.rasimalimgulov.reportapi.entity.Transaction;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.repository.ClientRepository;
import com.rasimalimgulov.reportapi.repository.TransactionRepository;
import com.rasimalimgulov.reportapi.repository.UserRepository;
import com.rasimalimgulov.reportapi.requests.TransactionIncomeRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    public TransactionService(TransactionRepository transactionRepository, ClientRepository clientRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public Transaction saveTransaction(TransactionIncomeRequest incomeRequest) {
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
}
