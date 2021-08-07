package com.jeespb.accountservice.dto;

public class RewardDto {

    private String rewardsAccountId;
    private Double rewardsBalance;
    private String rewardsType;
    private String rewardsRedeemStatus;
    private String rewardsExpiry;

    public String getRewardsAccountId() {
        return rewardsAccountId;
    }

    public void setRewardsAccountId(String rewardsAccountId) {
        this.rewardsAccountId = rewardsAccountId;
    }

    public Double getRewardsBalance() {
        return rewardsBalance;
    }

    public void setRewardsBalance(Double rewardsBalance) {
        this.rewardsBalance = rewardsBalance;
    }

    public String getRewardsType() {
        return rewardsType;
    }

    public void setRewardsType(String rewardsType) {
        this.rewardsType = rewardsType;
    }

    public String getRewardsRedeemStatus() {
        return rewardsRedeemStatus;
    }

    public void setRewardsRedeemStatus(String rewardsRedeemStatus) {
        this.rewardsRedeemStatus = rewardsRedeemStatus;
    }

    public String getRewardsExpiry() {
        return rewardsExpiry;
    }

    public void setRewardsExpiry(String rewardsExpiry) {
        this.rewardsExpiry = rewardsExpiry;
    }
}
