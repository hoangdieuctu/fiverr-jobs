package com.jeespb.databaseservice.controller;

import com.jeespb.databaseservice.model.Reward;
import com.jeespb.databaseservice.service.RewardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/domain/user")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @ResponseBody
    @GetMapping(value = "/rewards")
    public List<Reward> getRewards(@RequestParam("username") String username) {
        return rewardService.findByUsername(username);
    }

}
