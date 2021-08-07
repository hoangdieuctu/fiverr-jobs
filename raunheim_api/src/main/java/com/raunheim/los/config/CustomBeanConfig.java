package com.raunheim.los.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class CustomBeanConfig {

    @Bean
    public Validator validator() {
        var factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

}
