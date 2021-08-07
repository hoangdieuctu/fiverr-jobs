package com.jeespb.accountservice.dto.response;

import com.jeespb.accountservice.dto.AccountDto;

public class AccountResponseDto {
    private AccountDto[] accounts;

    public AccountResponseDto(AccountDto[] accounts) {
        this.accounts = accounts;
    }

    public AccountDto[] getAccounts() {
        return accounts;
    }

    public void setAccounts(AccountDto[] accounts) {
        this.accounts = accounts;
    }
}
