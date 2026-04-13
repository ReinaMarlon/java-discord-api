package com.marlonreina.discord.api.domain.model;

public class User {

    private String id;
    private String username;
    private String avatar;

    public User(String id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
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
}