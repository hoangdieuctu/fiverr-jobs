package com.jeespb.logoffservice.controller;

import com.jeespb.logoffservice.dto.request.SessionRequestDto;
import com.jeespb.logoffservice.dto.response.LogoffResponseDto;
import com.jeespb.logoffservice.service.LogoffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bank/user")
public class LogoffController {

    private final LogoffService logoffService;

    public LogoffController(LogoffService logoffService) {
        this.logoffService = logoffService;
    }

    @ResponseBody
    @DeleteMapping("/authentication")
    public LogoffResponseDto logoff(@RequestBody SessionRequestDto logoffRequestDto) {
        return logoffService.logoff(logoffRequestDto);
    }

}
