package com.greg.authservice.dto;

public class TokenDto {
    private String token;
    private Long expire;
    private String scope;

    public TokenDto(String token, Long expire, String scope) {
        this.token = token;
        this.expire = expire;
        this.scope = scope;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
