package com.andi.expennies.repositories;

import com.andi.expennies.domains.RecurrenceType;
import com.andi.expennies.domains.Transaction;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException;

    void create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException;

    void update(Integer userId, Integer categoryId, Integer transactionId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException;

    void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException;

    List<Transaction> getPaymentsScheduledForToday();

}
