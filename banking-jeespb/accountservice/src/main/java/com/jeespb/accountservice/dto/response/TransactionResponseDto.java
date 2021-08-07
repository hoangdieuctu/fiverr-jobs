package com.jeespb.accountservice.dto.response;

import com.jeespb.accountservice.dto.TransactionDto;

public class TransactionResponseDto {
    private TransactionDto[] transactions;

    public TransactionResponseDto(TransactionDto[] transactions) {
        this.transactions = transactions;
    }

    public TransactionDto[] getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionDto[] transactions) {
        this.transactions = transactions;
    }
}
