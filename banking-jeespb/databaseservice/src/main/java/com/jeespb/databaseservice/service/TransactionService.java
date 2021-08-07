package com.jeespb.databaseservice.service;

import com.jeespb.databaseservice.model.Transaction;
import com.jeespb.databaseservice.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}
