package com.greg.userservice.controller;

import com.greg.userservice.dto.UserDto;
import com.greg.userservice.model.User;
import com.greg.userservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @ResponseBody
    @GetMapping("/search")
    public User getByUsername(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }
}
