package com.jeespb.databaseservice.controller;

import com.jeespb.databaseservice.model.Account;
import com.jeespb.databaseservice.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/domain/user")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseBody
    @GetMapping(value = "/accountInfo")
    public List<Account> getAccountInfo(@RequestParam("username") String username) {
        return accountService.findByUsername(username);
    }

}
