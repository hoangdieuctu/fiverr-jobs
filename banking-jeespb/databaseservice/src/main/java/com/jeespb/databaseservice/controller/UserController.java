package com.jeespb.databaseservice.controller;

import com.jeespb.databaseservice.dto.request.AuthenticationRequestDto;
import com.jeespb.databaseservice.dto.request.LoginDetailRequestDto;
import com.jeespb.databaseservice.dto.request.SessionRequestDto;
import com.jeespb.databaseservice.model.User;
import com.jeespb.databaseservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/domain/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = "/authentication")
    public User authenticate(@RequestBody AuthenticationRequestDto requestDto) {
        return userService.authenticate(requestDto);
    }

    @ResponseBody
    @PostMapping(value = "/session")
    public User getSession(@RequestBody SessionRequestDto requestDto) {
        return userService.getSession(requestDto);
    }

    @ResponseBody
    @PostMapping(value = "/updateLoginDetails")
    public void updateLoginDetails(@RequestBody LoginDetailRequestDto requestDto) {
        userService.updateLoginDetails(requestDto);
    }
}
