package com.marlonreina.discord.api.domain.model;

import java.time.LocalDateTime;

public class User {

    private String id;
    private String username;
    private String avatar;
    private String email;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;

    public User(String id, String username, String avatar, String email,
                String accessToken, String refreshToken, LocalDateTime expiresAt) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public User(String id, String username, String avatar, String email) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

}