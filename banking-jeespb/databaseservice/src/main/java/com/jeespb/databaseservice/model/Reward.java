package com.jeespb.databaseservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "user_id", nullable = false)
    private String username;

    @Column(name = "rewards_account_id", nullable = false)
    private String rewardsAccountId;

    @Column(name = "rewards_balance", nullable = false)
    private Double rewardsBalance;

    @Column(name = "rewards_type", nullable = false)
    private String rewardsType;

    @Column(name = "rewards_redeem_status", nullable = false)
    private String rewardsRedeemStatus;

    @Column(name = "rewards_expiry", nullable = false)
    private String rewardsExpiry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
