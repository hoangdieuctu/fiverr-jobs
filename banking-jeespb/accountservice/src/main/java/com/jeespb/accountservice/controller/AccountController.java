package com.jeespb.accountservice.controller;

import com.jeespb.accountservice.config.ValidSession;
import com.jeespb.accountservice.dto.AccountDto;
import com.jeespb.accountservice.dto.RewardDto;
import com.jeespb.accountservice.dto.TransactionDto;
import com.jeespb.accountservice.dto.request.AccountInfoRequestDto;
import com.jeespb.accountservice.dto.request.TransactionRequestDto;
import com.jeespb.accountservice.dto.response.AccountResponseDto;
import com.jeespb.accountservice.dto.response.RewardResponseDto;
import com.jeespb.accountservice.dto.response.TransactionResponseDto;
import com.jeespb.accountservice.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bank/user")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ValidSession
    @ResponseBody
    @PostMapping("/dashboard/accountInfo")
    public AccountResponseDto getAccountInfo(@RequestBody AccountInfoRequestDto requestDto) {
        AccountDto[] accounts = accountService.getAccountInfo(requestDto.getUserID());
        return new AccountResponseDto(accounts);
    }

    @ValidSession
    @ResponseBody
    @PostMapping("/dashboard/transactions")
    public TransactionResponseDto getTransactions(@RequestBody TransactionRequestDto requestDto) {
        TransactionDto[] transactions = accountService.getTransactions(requestDto.getAccountNumber());
        return new TransactionResponseDto(transactions);
    }

    @ResponseBody
    @GetMapping("/rewards/{userId}")
    public RewardResponseDto getRewards(@RequestHeader("sessionID") String sessionId,
                                        @PathVariable("userId") String username) {
        RewardDto[] rewards = accountService.getRewards(username);
        return new RewardResponseDto(rewards);
    }
}
