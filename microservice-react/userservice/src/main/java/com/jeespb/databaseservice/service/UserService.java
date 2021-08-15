package com.jeespb.databaseservice.service;

import com.jeespb.databaseservice.model.User;
import com.jeespb.databaseservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> results = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        users.forEach(u -> results.add(u));
        return results;
    }
}
