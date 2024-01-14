package com.andi.expennies.repositories;

import com.andi.expennies.domains.RecurrenceType;
import com.andi.expennies.domains.Transaction;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SQL_FIND_ALL = "SELECT * FROM EP_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_FIND_ALL_ACROSS = "SELECT * FROM EP_TRANSACTIONS";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM EP_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO EP_TRANSACTIONS (TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE, IS_RECURRING, RECURRENCE_TYPE) VALUES(NEXTVAL('EP_TRANSACTIONS_SEQ'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE EP_TRANSACTIONS SET AMOUNT = ?, NOTE = ?, TRANSACTION_DATE = ?, IS_RECURRING = ?, RECURRENCE_TYPE = ? WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM EP_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId, categoryId}, transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId, transactionId}, transactionRowMapper);
        } catch (Exception e) {
            throw new EpResourceNotFoundException("Could not find transaction");
        }
    }

    @Override
    public void create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException {
        jdbcTemplate.update(SQL_CREATE, categoryId, userId, amount, note, transactionDate, isRecurring, recurrenceType.name());
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Double amount, String note, Long transactionDate, Boolean isRecurring, RecurrenceType recurrenceType) throws EpBadRequestException {
        jdbcTemplate.update(SQL_UPDATE, amount, note, transactionDate, isRecurring, recurrenceType.name(), userId, categoryId, transactionId);
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EpResourceNotFoundException {
        jdbcTemplate.update(SQL_DELETE, userId, categoryId, transactionId);
    }

    @Override
    public List<Transaction> getPaymentsScheduledForToday() {
        LocalDate today = LocalDate.now();

        List<Transaction> allTransactionsAcross = jdbcTemplate.query(SQL_FIND_ALL_ACROSS, new Object[]{}, transactionRowMapper);

        return allTransactionsAcross.stream()
                .filter(transaction -> isPaymentToday(transaction, today))
                .collect(Collectors.toList());
    }

    private boolean isPaymentToday(Transaction transaction, LocalDate today) {
        Long transactionEpochTime = transaction.getTransactionDate();

        if (transactionEpochTime == null) return false;
        if (!transaction.getRecurring()) return false;

        LocalDate transactionDate = LocalDate.ofInstant(Instant.ofEpochSecond(transactionEpochTime), ZoneId.systemDefault());
        return today.isEqual(transactionDate);
    }

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        RecurrenceType recurringType = RecurrenceType.valueOf(rs.getString("RECURRENCE_TYPE").toUpperCase());
        return new Transaction(rs.getInt("TRANSACTION_ID"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE"),
                rs.getBoolean("IS_RECURRING"),
                recurringType
                );
    });
}
