package com.marlonreina.discord.api.domain.model;

public class DiscordUser {

    private String id;
    private String username;
    private String avatar;

    public DiscordUser(String id, String username, String avatar) {
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