package com.jeespb.accountservice.dto;

public enum ErrorType {
    SESSION_INACTIVE("The session is inactive, please login again.");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
