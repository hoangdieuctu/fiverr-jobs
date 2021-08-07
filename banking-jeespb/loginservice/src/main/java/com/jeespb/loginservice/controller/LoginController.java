package com.jeespb.loginservice.controller;

import com.jeespb.loginservice.dto.LoginDto;
import com.jeespb.loginservice.dto.request.LoginRequestDto;
import com.jeespb.loginservice.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bank/user")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ResponseBody
    @PostMapping("/authentication")
    public LoginDto authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.authenticate(loginRequestDto);
    }

}
