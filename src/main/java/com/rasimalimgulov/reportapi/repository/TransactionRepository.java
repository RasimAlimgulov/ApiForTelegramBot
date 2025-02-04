package com.rasimalimgulov.reportapi.repository;

import com.rasimalimgulov.reportapi.entity.Transaction;
import com.rasimalimgulov.reportapi.service.report.dto.ExpenseByCategoryDTO;
import com.rasimalimgulov.reportapi.service.report.dto.IncomeByCategoryDTO;
import com.rasimalimgulov.reportapi.service.report.dto.IncomeByClientDTO;
import com.rasimalimgulov.reportapi.service.report.dto.TotalReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        // 1. Доходы по клиентам
        @Query("SELECT new com.rasimalimgulov.reportapi.service.report.dto.IncomeByClientDTO(t.client.fullName, SUM(t.amount)) " +
                "FROM Transaction t " +
                "WHERE t.user.username = :username AND t.type = 'INCOME' " +
                "GROUP BY t.client.fullName")
        List<IncomeByClientDTO> findIncomeByUsernameGroupedByClients(@Param("username") String username);

        // 2. Доходы по категориям услуг
        @Query("SELECT new com.rasimalimgulov.reportapi.service.report.dto.IncomeByCategoryDTO(t.serviceType.name, SUM(t.amount)) " +
                "FROM Transaction t " +
                "WHERE t.user.username = :username AND t.type = 'INCOME' " +
                "GROUP BY t.serviceType.name")
        List<IncomeByCategoryDTO> findIncomeByUsernameGroupedByCategories(@Param("username") String username);

        // 3. Расходы по категориям
        @Query("SELECT new com.rasimalimgulov.reportapi.service.report.dto.ExpenseByCategoryDTO(t.category.name, SUM(t.amount)) " +
                "FROM Transaction t " +
                "WHERE t.user.username = :username AND t.type = 'EXPENSE' " +
                "GROUP BY t.category.name")
        List<ExpenseByCategoryDTO> findExpensesByUsernameGroupedByCategories(@Param("username") String username);

        // 4. Полный отчёт (все транзакции)
        @Query("SELECT new com.rasimalimgulov.reportapi.service.report.dto.TotalReportDTO(t.type, SUM(t.amount)) " +
                "FROM Transaction t " +
                "WHERE t.user.username = :username " +
                "GROUP BY t.type")
        List<TotalReportDTO> findAllByUsername(@Param("username") String username);
    }

