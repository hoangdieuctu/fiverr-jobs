package com.jeespb.loginservice.dto.request;

public class AuthenticationRequestDto {
    private String username;

    public AuthenticationRequestDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
