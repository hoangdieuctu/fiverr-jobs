package com.jeespb.databaseservice.controller;

import com.jeespb.databaseservice.model.Access;
import com.jeespb.databaseservice.repository.AccessRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/access")
public class AccessController {

    private final AccessRepository accessRepository;

    public AccessController(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    @ResponseBody
    @GetMapping
    public List<Access> getAccess() {
        PageRequest page = PageRequest.of(0, 10);
        return accessRepository.findByType("filter", page).getContent();
    }

}
