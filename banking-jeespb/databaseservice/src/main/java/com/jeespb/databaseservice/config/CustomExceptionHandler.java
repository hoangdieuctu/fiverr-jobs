package com.jeespb.databaseservice.config;

import com.jeespb.databaseservice.dto.response.ErrorResponseDto;
import com.jeespb.databaseservice.exception.ServiceException;
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
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleServiceException(ServiceException exception) {
        logger.info("Error: {}", exception.getMessage());
        return new ErrorResponseDto("Bad request", exception.getMessage());
    }
}
