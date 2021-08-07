package com.jeespb.databaseservice.service;

import com.jeespb.databaseservice.model.Account;
import com.jeespb.databaseservice.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
