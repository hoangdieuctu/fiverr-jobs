package com.jeespb.logoffservice.service.downstream;

import com.jeespb.logoffservice.dto.UserDto;
import com.jeespb.logoffservice.dto.request.LoginDetailRequestDto;
import com.jeespb.logoffservice.dto.request.SessionRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DatabaseService {

    private static Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Value("${microservices.databaseService.session}")
    private String sessionUrl;

    @Value("${microservices.databaseService.loginDetails}")
    private String loginDetailsUrl;

    private final RestTemplate restTemplate;

    public DatabaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getSession(SessionRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SessionRequestDto> entity = new HttpEntity<>(requestDto, headers);
        try {
            ResponseEntity<UserDto> response = restTemplate.postForEntity(sessionUrl, entity, UserDto.class);
            return response.getBody();
        } catch (Exception ex) {
            logger.info("Exception when get session: {}", ex.getMessage());
            return null;
        }
    }

    public void updateLoginDetails(LoginDetailRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginDetailRequestDto> entity = new HttpEntity<>(requestDto, headers);
        try {
            restTemplate.postForEntity(loginDetailsUrl, entity, Void.class);
        } catch (Exception ex) {
            logger.info("Exception when update login details: {}", ex.getMessage());
        }
    }
}
