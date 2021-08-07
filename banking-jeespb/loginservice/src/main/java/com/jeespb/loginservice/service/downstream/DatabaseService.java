package com.jeespb.loginservice.service.downstream;

import com.jeespb.loginservice.dto.UserDto;
import com.jeespb.loginservice.dto.request.AuthenticationRequestDto;
import com.jeespb.loginservice.dto.request.LoginDetailRequestDto;
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

    @Value("${microservices.databaseService.authentication}")
    private String authenticationUrl;

    @Value("${microservices.databaseService.loginDetails}")
    private String loginDetailsUrl;

    private final RestTemplate restTemplate;

    public DatabaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto authenticate(String username) {
        AuthenticationRequestDto request = new AuthenticationRequestDto(username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequestDto> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<UserDto> response = restTemplate.postForEntity(authenticationUrl, entity, UserDto.class);
            return response.getBody();
        } catch (Exception ex) {
            logger.info("Exception when authentication: {}", ex.getMessage());
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
