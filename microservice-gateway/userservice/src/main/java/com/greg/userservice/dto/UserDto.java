package com.greg.userservice.dto;

public class UserDto {
    private Long id;
    private String username;
    private String scope;
    private String fullName;

    public UserDto(Long id, String username, String scope, String fullName) {
        this.id = id;
        this.username = username;
        this.scope = scope;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
