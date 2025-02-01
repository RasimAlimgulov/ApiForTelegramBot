package com.rasimalimgulov.reportapi.repository;

import com.rasimalimgulov.reportapi.entity.ExpenseCategory;
import com.rasimalimgulov.reportapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategory,Long> {
List<ExpenseCategory> findAllByUser(User user);
}
