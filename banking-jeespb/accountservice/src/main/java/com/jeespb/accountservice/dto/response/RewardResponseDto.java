package com.jeespb.accountservice.dto.response;

import com.jeespb.accountservice.dto.RewardDto;

public class RewardResponseDto {
    private RewardDto[] rewards;

    public RewardResponseDto(RewardDto[] rewards) {
        this.rewards = rewards;
    }

    public RewardDto[] getRewards() {
        return rewards;
    }

    public void setRewards(RewardDto[] rewards) {
        this.rewards = rewards;
    }
}
