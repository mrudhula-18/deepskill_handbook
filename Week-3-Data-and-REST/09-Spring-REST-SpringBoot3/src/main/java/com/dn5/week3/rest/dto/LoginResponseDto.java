package com.dn5.week3.rest.dto;

public class LoginResponseDto {

    private String token;
    private String tokenType = "Bearer";

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
