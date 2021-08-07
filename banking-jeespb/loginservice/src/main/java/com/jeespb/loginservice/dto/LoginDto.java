package com.jeespb.loginservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LoginDto {
    private LoginStatus loginStatus;
    private String customerName;
    private String customerType;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date lastLoginDate;
    private String sessionID;

    public static LoginDto invalid() {
        LoginDto loginDto = new LoginDto();
        loginDto.setLoginStatus(LoginStatus.INVALID);
        return loginDto;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
