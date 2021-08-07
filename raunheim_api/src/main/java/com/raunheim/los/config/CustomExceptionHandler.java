package com.raunheim.los.config;

import com.raunheim.los.dto.response.ResponseDto;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.exception.LosValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(LosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleLosException(LosException ex) {
        log.warn("Los exception: {}", ex.getMessage());
        return ResponseDto.error(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(LosValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleLosValidationException(LosValidationException ex) {
        log.warn("Los validation exception");
        return ResponseDto.validateError(ex.getMessage(), ex.getValidatorMessages());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto handleException(Exception ex) {
        log.warn("Exception: ", ex);
        return ResponseDto.error("Internal server error");
    }
}
