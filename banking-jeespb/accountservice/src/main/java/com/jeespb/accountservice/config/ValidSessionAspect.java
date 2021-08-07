package com.jeespb.accountservice.config;

import com.jeespb.accountservice.dto.SessionStatus;
import com.jeespb.accountservice.dto.UserDto;
import com.jeespb.accountservice.dto.request.RequestDto;
import com.jeespb.accountservice.dto.request.SessionRequestDto;
import com.jeespb.accountservice.exception.InvalidSessionException;
import com.jeespb.accountservice.service.downstream.DatabaseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidSessionAspect {

    private final DatabaseService databaseService;

    public ValidSessionAspect(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Before(value = "@annotation(ValidSession)")
    public void before(JoinPoint joinPoint) {
        RequestDto requestDto = getRequestDto(joinPoint);
        String sessionId = requestDto.getSessionID();
        if (sessionId == null || sessionId.isEmpty()) {
            throw new InvalidSessionException("Missing session ID");
        }
        SessionRequestDto sessionRequestDto = new SessionRequestDto();
        sessionRequestDto.setSessionID(sessionId);
        UserDto userDto = databaseService.getSession(sessionRequestDto);
        if (userDto == null) {
            throw new InvalidSessionException("Invalid session");
        }
        SessionStatus status = userDto.getSessionStatus();
        if (!SessionStatus.ACTIVE.equals(status)) {
            throw new InvalidSessionException("Session status is invalid");
        }
    }

    private RequestDto getRequestDto(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        for (Object signatureArg : signatureArgs) {
            if (signatureArg instanceof RequestDto) {
                return (RequestDto) signatureArg;
            }
        }
        throw new InvalidSessionException("Request object with session not found");
    }
}
