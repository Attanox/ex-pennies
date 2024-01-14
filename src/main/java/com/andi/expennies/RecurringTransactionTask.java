package com.andi.expennies;

import com.andi.expennies.domains.Transaction;
import com.andi.expennies.repositories.TransactionRepository;
import com.andi.expennies.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Component
public class RecurringTransactionTask {
    @Autowired
    TransactionRepository transactionRepository;

    @Scheduled(fixedRate = 60000) // Run daily at midnight
    public void generateRecurringExpenses() {
        // Fetch upcoming recurring expenses
        List<Transaction> recurringExpenses = transactionRepository.getPaymentsScheduledForToday();

        recurringExpenses.forEach((transaction -> {
            // Modify transaction date based on recurrence type
            LocalDate newTransactionDate = calculateNewTransactionDate(transaction);

            long newTransactionDateEpoch = newTransactionDate.toEpochSecond(LocalTime.NOON, ZoneOffset.UTC);
            // Save the new transaction to the database
            transactionRepository.create(
                    transaction.getUser_id(),
                    transaction.getCategory_id(),
                    transaction.getAmount(),
                    transaction.getNote(),
                    newTransactionDateEpoch,
                    transaction.getRecurring(),
                    transaction.getRecurrenceType()
            );
        }));
    }

    private LocalDate calculateNewTransactionDate(Transaction transaction) {
        LocalDate today = LocalDate.now();

        switch (transaction.getRecurrenceType()) {
            case DAILY:
                return today.plusDays(1);
            case WEEKLY:
                return today.plusWeeks(1);
            case MONTHLY:
                return today.plusMonths(1);
            case YEARLY:
                return today.plusYears(1);
            default:
                throw new IllegalArgumentException("Unsupported recurrence type");
        }
    }
}
