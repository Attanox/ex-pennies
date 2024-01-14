package com.andi.expennies.services;

import com.andi.expennies.domains.RecurrenceType;
import com.andi.expennies.domains.Transaction;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException;

    void addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException;

    void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException;

    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException;

}
