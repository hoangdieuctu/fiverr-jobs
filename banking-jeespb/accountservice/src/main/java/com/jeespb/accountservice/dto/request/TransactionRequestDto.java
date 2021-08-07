package com.jeespb.accountservice.dto.request;

public class TransactionRequestDto extends RequestDto {
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
