package com.jeespb.logoffservice.dto.response;

import com.jeespb.logoffservice.dto.LoginStatus;

public class LogoffResponseDto {
    private LoginStatus loginStatus;

    public LogoffResponseDto(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
}
