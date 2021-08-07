package com.jeespb.loginservice.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoginStatus {
    VALID("Valid"),
    INVALID("Invalid"),
    INVALID_UUID("Invalid UUID");

    private String value;

    LoginStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
