package com.jeespb.accountservice.service;

import com.jeespb.accountservice.dto.AccountDto;
import com.jeespb.accountservice.dto.RewardDto;
import com.jeespb.accountservice.dto.TransactionDto;
import com.jeespb.accountservice.service.downstream.DatabaseService;
import org.springframework.stereotype.Component;

@Component
public class AccountService {

    private final DatabaseService databaseService;

    public AccountService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public AccountDto[] getAccountInfo(String userId) {
        return databaseService.getAccountInfo(userId);
    }

    public TransactionDto[] getTransactions(String accountNumber) {
        return databaseService.getTransactions(accountNumber);
    }

    public RewardDto[] getRewards(String username) {
        return databaseService.getRewards(username);
    }
}
