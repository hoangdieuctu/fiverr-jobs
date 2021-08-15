package com.greg.gateway.filter;

import com.greg.gateway.exception.JwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String HEADER_AUTH_KEY = "Authorization";

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            try {
                validateJwtToken(exchange.getRequest());
            } catch (JwtException ex) {
                logger.info("Jwt exception: {}", ex.getMessage());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        var response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private void validateJwtToken(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey(HEADER_AUTH_KEY)) {
            throw new JwtException("Authorization header is missing");
        }
        var headers = request.getHeaders().get(HEADER_AUTH_KEY);
        if (headers == null || headers.isEmpty()) {
            throw new JwtException("Authorization header is empty");
        }
        var token = headers.get(0).trim();
        if (token.isEmpty()) {
            throw new JwtException("Authorization token is empty");
        }
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Authorization token is expired");
        }
    }

    public static class Config {
    }

}
