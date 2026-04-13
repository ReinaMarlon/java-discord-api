package com.marlonreina.discord.api.domain.model;

public class User {

    private String id;
    private String username;
    private String avatar;
    private String email;

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
}