package com.marlonreina.discord.api.shared.dto;

import com.marlonreina.discord.api.domain.model.User;

public class AuthResponse {

    private String token;
    private User user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}