package com.jeespb.accountservice.dto.request;

public class AccountInfoRequestDto extends RequestDto {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
