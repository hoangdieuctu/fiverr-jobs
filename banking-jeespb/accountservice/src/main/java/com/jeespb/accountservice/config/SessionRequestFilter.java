package com.jeespb.accountservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeespb.accountservice.dto.ErrorType;
import com.jeespb.accountservice.dto.SessionStatus;
import com.jeespb.accountservice.dto.UserDto;
import com.jeespb.accountservice.dto.request.SessionRequestDto;
import com.jeespb.accountservice.dto.response.ErrorResponseDto;
import com.jeespb.accountservice.service.downstream.DatabaseService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
public class SessionRequestFilter implements Filter {

    private static final String SESSION_ID_HEADER = "sessionID";

    private final DatabaseService databaseService;
    private final ObjectMapper objectMapper;

    public SessionRequestFilter(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String sessionId = httpRequest.getHeader(SESSION_ID_HEADER);
        if (sessionId != null) {
            SessionRequestDto sessionRequestDto = new SessionRequestDto();
            sessionRequestDto.setSessionID(sessionId);
            UserDto userDto = databaseService.getSession(sessionRequestDto);
            if (userDto == null) {
                sendErrorResponse(response);
                return;
            }
            SessionStatus status = userDto.getSessionStatus();
            if (!SessionStatus.ACTIVE.equals(status)) {
                sendErrorResponse(response);
                return;
            }
        }

        filter.doFilter(request, response);
    }

    private void sendErrorResponse(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ErrorResponseDto responseDto = ErrorResponseDto.from(ErrorType.SESSION_INACTIVE);
        String jsonBody = objectMapper.writeValueAsString(responseDto);

        httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        httpResponse.setContentType("application/json");
        httpResponse.getWriter().write(jsonBody);
    }
}
