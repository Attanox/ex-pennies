package com.andi.expennies.resources;

import com.andi.expennies.domains.RecurrenceType;
import com.andi.expennies.domains.Transaction;
import com.andi.expennies.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
                                                                @PathVariable("categoryId") Integer categoryId) {
        int userId = (Integer) request.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId, categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request,
                                                          @PathVariable("categoryId") Integer categoryId,
                                                          @PathVariable("transactionId") Integer transactionId) {
        int userId = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionById(userId, categoryId, transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> createTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Map<String, Object> transactionMap
    ) {
        int userId = (Integer) request.getAttribute("userId");
        Double amount = (Double) transactionMap.get("amount");
        String note = (String) transactionMap.get("note");
        Long transactionDate = null;
        Object transactionDateObj = transactionMap.get("transactionDate");
        if (transactionDateObj instanceof Long) {
            transactionDate = (Long) transactionDateObj;
        } else if (transactionDateObj instanceof Integer) {
            transactionDate = ((Integer) transactionDateObj).longValue(); // Convert Integer to Long
        }
        Boolean isRecurring = (Boolean) transactionMap.get("isRecurring");

        String recurrenceTypeStr = (String) transactionMap.get("recurrenceType");
        RecurrenceType recurrenceType = RecurrenceType.valueOf(recurrenceTypeStr);

        transactionService.addTransaction(userId, categoryId, amount, note, transactionDate, isRecurring, recurrenceType);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId,
            @RequestBody Map<String, Object> transactionMap
    ) {
        int userId = (Integer) request.getAttribute("userId");
        Double amount = (Double) transactionMap.get("amount");
        String note = (String) transactionMap.get("note");
        Long transactionDate = null;
        Object transactionDateObj = transactionMap.get("transactionDate");
        if (transactionDateObj instanceof Long) {
            transactionDate = (Long) transactionDateObj;
        } else if (transactionDateObj instanceof Integer) {
            transactionDate = ((Integer) transactionDateObj).longValue(); // Convert Integer to Long
        }
        Boolean isRecurring = (Boolean) transactionMap.get("isRecurring");

        String recurrenceTypeStr = (String) transactionMap.get("recurrenceType");
        RecurrenceType recurrenceType = RecurrenceType.valueOf(recurrenceTypeStr);

        transactionService.updateTransaction(userId, categoryId, transactionId, amount, note, transactionDate, isRecurring, recurrenceType);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId
    ) {
        int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId, categoryId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
