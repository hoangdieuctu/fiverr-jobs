package com.jeespb.logoffservice.service;

import com.jeespb.logoffservice.dto.LoginStatus;
import com.jeespb.logoffservice.dto.SessionStatus;
import com.jeespb.logoffservice.dto.UserDto;
import com.jeespb.logoffservice.dto.request.LoginDetailRequestDto;
import com.jeespb.logoffservice.dto.request.SessionRequestDto;
import com.jeespb.logoffservice.dto.response.LogoffResponseDto;
import com.jeespb.logoffservice.service.downstream.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LogoffService {

    private final DatabaseService databaseService;

    public LogoffService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public LogoffResponseDto logoff(SessionRequestDto requestDto) {
        UserDto userDto = databaseService.getSession(requestDto);
        if (userDto == null) {
            return new LogoffResponseDto(LoginStatus.INVALID_UUID);
        }
        if (!Objects.equals(SessionStatus.ACTIVE, userDto.getSessionStatus())) {
            return new LogoffResponseDto(LoginStatus.INVALID_UUID);
        }

        LoginDetailRequestDto loginDetailRequestDto = new LoginDetailRequestDto();
        loginDetailRequestDto.setSessionStatus(SessionStatus.INACTIVE);
        loginDetailRequestDto.setSessionId(userDto.getCustomerSessionId());
        loginDetailRequestDto.setUsername(userDto.getUsername());
        databaseService.updateLoginDetails(loginDetailRequestDto);

        return new LogoffResponseDto(LoginStatus.INVALID);
    }

}
