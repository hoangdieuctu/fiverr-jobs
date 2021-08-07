package com.raunheim.los.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.hosts}")
    private List<String> hosts;

    @Bean
    public OpenAPI openApi() {
        var servers = hosts.stream().map(host -> {
            var server = new Server();
            server.setUrl(host);
            return server;
        }).collect(Collectors.toList());
        return new OpenAPI().servers(servers);
    }

}
