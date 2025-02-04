package com.rasimalimgulov.reportapi.service.report;

import com.rasimalimgulov.reportapi.entity.Transaction;
import com.rasimalimgulov.reportapi.repository.TransactionRepository;
import com.rasimalimgulov.reportapi.repository.UserRepository;
import com.rasimalimgulov.reportapi.service.report.dto.ExpenseByCategoryDTO;
import com.rasimalimgulov.reportapi.service.report.dto.IncomeByCategoryDTO;
import com.rasimalimgulov.reportapi.service.report.dto.IncomeByClientDTO;
import com.rasimalimgulov.reportapi.service.report.dto.TotalReportDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import static com.rasimalimgulov.reportapi.service.report.ReportType.*;
@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;

    // 1. Доходы по клиентам
    public byte[] generateIncomeReportByClients(String username) {
        List<IncomeByClientDTO> transactions = transactionRepository.findIncomeByUsernameGroupedByClients(username);
        return createExcelReport(transactions, INCOME_BY_CLIENT, "Доходы по клиентам");
    }

    // 2. Доходы по категориям услуг
    public byte[] generateIncomeReportByCategories(String username) {
        List<IncomeByCategoryDTO> transactions = transactionRepository.findIncomeByUsernameGroupedByCategories(username);
        return createExcelReport(transactions, INCOME_BY_CATEGORY, "Доходы по категориям");
    }

    // 3. Расходы по категориям
    public byte[] generateExpenseReportByCategories(String username) {
        List<ExpenseByCategoryDTO> transactions = transactionRepository.findExpensesByUsernameGroupedByCategories(username);
        return createExcelReport(transactions, EXPENSE_BY_CATEGORY, "Расходы по категориям");
    }

    // 4. Общий отчёт
    public byte[] generateTotalReport(String username) {
        List<TotalReportDTO> transactions = transactionRepository.findAllByUsername(username);
        return createExcelReport(transactions, TOTAL_REPORT, "Общий отчёт");
    }

    private byte[] createExcelReport(List<?> reportData, ReportType reportType, String reportTitle) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(reportTitle);
            Row headerRow = sheet.createRow(0);
            String[] columns;

            switch (reportType) {
                case INCOME_BY_CLIENT:
                    columns = new String[]{"Клиент", "Доход"};
                    break;
                case INCOME_BY_CATEGORY:
                    columns = new String[]{"Категория услуги", "Доход"};
                    break;
                case EXPENSE_BY_CATEGORY:
                    columns = new String[]{"Категория расхода", "Сумма"};
                    break;
                case TOTAL_REPORT:
                    columns = new String[]{"Тип", "Общая сумма"};
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемый тип отчёта: " + reportType);
            }

            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (Object data : reportData) {
                Row row = sheet.createRow(rowNum++);

                switch (reportType) {
                    case INCOME_BY_CLIENT:
                        IncomeByClientDTO incomeByClient = (IncomeByClientDTO) data;
                        row.createCell(0).setCellValue(incomeByClient.getClientName());
                        row.createCell(1).setCellValue(incomeByClient.getTotalIncome());
                        break;

                    case INCOME_BY_CATEGORY:
                        IncomeByCategoryDTO incomeByCategory = (IncomeByCategoryDTO) data;
                        row.createCell(0).setCellValue(incomeByCategory.getServiceType());
                        row.createCell(1).setCellValue(incomeByCategory.getTotalIncome());
                        break;

                    case EXPENSE_BY_CATEGORY:
                        ExpenseByCategoryDTO expenseByCategory = (ExpenseByCategoryDTO) data;
                        row.createCell(0).setCellValue(expenseByCategory.getCategoryName());
                        row.createCell(1).setCellValue(expenseByCategory.getTotalExpense());
                        break;

                    case TOTAL_REPORT:
                        TotalReportDTO total = (TotalReportDTO) data;
                        row.createCell(0).setCellValue(total.getTransactionType().name());
                        row.createCell(1).setCellValue(total.getTotalAmount());
                        break;
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании Excel-файла", e);
        }
    }

}

