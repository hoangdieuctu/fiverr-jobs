package com.jeespb.databaseservice.controller;

import com.jeespb.databaseservice.model.Transaction;
import com.jeespb.databaseservice.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/domain/user")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseBody
    @GetMapping(value = "/transactions")
    public List<Transaction> getTransactions(@RequestParam("accountNumber") String accountNumber) {
        return transactionService.findByAccountNumber(accountNumber);
    }

}
