package com.jeespb.databaseservice.service;

import com.jeespb.databaseservice.dto.request.AuthenticationRequestDto;
import com.jeespb.databaseservice.dto.request.LoginDetailRequestDto;
import com.jeespb.databaseservice.dto.request.SessionRequestDto;
import com.jeespb.databaseservice.exception.ServiceException;
import com.jeespb.databaseservice.model.SessionStatus;
import com.jeespb.databaseservice.model.User;
import com.jeespb.databaseservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(AuthenticationRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername());
        if (user == null) {
            throw new ServiceException(String.format("User '%s' not found.", requestDto.getUsername()));
        }
        if (SessionStatus.ACTIVE.equals(user.getSessionStatus())) {
            throw new ServiceException("User session is active");
        }
        return user;
    }

    public User getSession(SessionRequestDto requestDto) {
        User user = userRepository.findByCustomerSessionId(requestDto.getSessionID());
        if (user == null) {
            throw new ServiceException(String.format("Session '%s' not found.", requestDto.getSessionID()));
        }
        return user;
    }

    public void updateLoginDetails(LoginDetailRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername());
        if (user == null) {
            throw new ServiceException(String.format("User '%s' not found.", requestDto.getUsername()));
        }
        if (requestDto.getSessionStatus() != null) {
            user.setSessionStatus(requestDto.getSessionStatus());
        }
        if (requestDto.getSessionId() != null) {
            user.setCustomerSessionId(requestDto.getSessionId());
        }
        if (requestDto.getLastLoginDate() != null) {
            user.setLastLoginDate(new Date());
        }
        userRepository.save(user);
    }
}
