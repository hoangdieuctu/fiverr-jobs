package com.jeespb.loginservice.service;

import com.jeespb.loginservice.dto.LoginDto;
import com.jeespb.loginservice.dto.LoginStatus;
import com.jeespb.loginservice.dto.SessionStatus;
import com.jeespb.loginservice.dto.UserDto;
import com.jeespb.loginservice.dto.request.LoginDetailRequestDto;
import com.jeespb.loginservice.dto.request.LoginRequestDto;
import com.jeespb.loginservice.service.downstream.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class LoginService {

    private final DatabaseService databaseService;

    public LoginService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public LoginDto authenticate(LoginRequestDto loginRequestDto) {
        UserDto userDto = databaseService.authenticate(loginRequestDto.getUserId());
        if (userDto == null) {
            return LoginDto.invalid();
        }
        if (!Objects.equals(userDto.getPassword(), loginRequestDto.getPassword())) {
            return LoginDto.invalid();
        }
        String sessionId = UUID.randomUUID().toString();

        LoginDetailRequestDto requestDto = new LoginDetailRequestDto();
        requestDto.setSessionStatus(SessionStatus.ACTIVE);
        requestDto.setLastLoginDate(new Date());
        requestDto.setSessionId(sessionId);
        requestDto.setUsername(userDto.getUsername());
        databaseService.updateLoginDetails(requestDto);

        LoginDto loginDto = new LoginDto();
        loginDto.setLoginStatus(LoginStatus.VALID);
        loginDto.setCustomerName(userDto.getCustomerName());
        loginDto.setCustomerType(userDto.getCustomerType());
        loginDto.setLastLoginDate(userDto.getLastLoginDate());
        loginDto.setSessionID(sessionId);
        return loginDto;
    }

}
