package com.jeespb.accountservice.config;

import com.jeespb.accountservice.dto.ErrorType;
import com.jeespb.accountservice.dto.response.ErrorResponseDto;
import com.jeespb.accountservice.exception.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = InvalidSessionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidSessionException(InvalidSessionException exception) {
        logger.warn("Exception: {}", exception.getMessage());
        return ErrorResponseDto.from(ErrorType.SESSION_INACTIVE);
    }


}
