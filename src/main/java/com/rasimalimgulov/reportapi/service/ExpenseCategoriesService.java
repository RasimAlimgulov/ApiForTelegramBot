package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.ExpenseCategory;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.repository.ExpenseCategoriesRepository;
import com.rasimalimgulov.reportapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoriesService {
private final ExpenseCategoriesRepository expenseCategoriesRepository;
private final UserRepository userRepository;
    public ExpenseCategoriesService(ExpenseCategoriesRepository expenseCategoriesRepository, UserRepository userRepository) {
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.userRepository = userRepository;
    }
    public List<ExpenseCategory> getAllExpenseCategoriesByUserId(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return expenseCategoriesRepository.findAllByUser(user);
    }
    public ExpenseCategory addExpenseCategory(String nameCategory,String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        ExpenseCategory expenseCategory=new ExpenseCategory();
        expenseCategory.setName(nameCategory);
        expenseCategory.setUser(user);
        return expenseCategoriesRepository.save(expenseCategory);
    }
}

