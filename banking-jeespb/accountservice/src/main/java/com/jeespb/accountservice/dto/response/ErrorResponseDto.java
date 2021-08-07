package com.jeespb.accountservice.dto.response;

import com.jeespb.accountservice.dto.ErrorType;

public class ErrorResponseDto {
    private String errorCode;
    private String errorMessage;

    public static ErrorResponseDto from(ErrorType errorType) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setErrorCode(errorType.name());
        response.setErrorMessage(errorType.getMessage());
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
