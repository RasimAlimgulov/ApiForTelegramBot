package com.rasimalimgulov.reportapi.repository;

import com.rasimalimgulov.reportapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
