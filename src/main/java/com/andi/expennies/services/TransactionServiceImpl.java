package com.andi.expennies.services;

import com.andi.expennies.domains.RecurrenceType;
import com.andi.expennies.domains.Transaction;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;
import com.andi.expennies.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
        return transactionRepository.findAll(userId, categoryId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException {
        return transactionRepository.findById(userId, categoryId, transactionId);
    }

    @Override
    public void addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException {
        transactionRepository.create(userId, categoryId, amount, note, transactionDate, isRecurring, recurrenceType);
    }


    @Override
    public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException {
        transactionRepository.update(userId, categoryId, transactionId, amount, note, transactionDate, isRecurring, recurrenceType);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException {
        transactionRepository.removeById(userId, categoryId, transactionId);
    }
}
