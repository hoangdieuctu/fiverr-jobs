package com.jeespb.databaseservice.service;

import com.jeespb.databaseservice.model.Reward;
import com.jeespb.databaseservice.repository.RewardRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardService {

    private final RewardRepository rewardRepository;

    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public List<Reward> findByUsername(String username) {
        return rewardRepository.findByUsername(username);
    }
}
