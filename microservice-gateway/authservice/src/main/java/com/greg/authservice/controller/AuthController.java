package com.greg.authservice.controller;

import com.greg.authservice.dto.AuthDto;
import com.greg.authservice.dto.TokenDto;
import com.greg.authservice.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseBody
    @PostMapping("/login")
    public TokenDto login(@RequestBody AuthDto auth) {
        return authService.login(auth);
    }

}
