package com.marlonreina.discord.api.domain.model;

public class DiscordMember {

    private final String id;
    private final String username;

    public DiscordMember(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
