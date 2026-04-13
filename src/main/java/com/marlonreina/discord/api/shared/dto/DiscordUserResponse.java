package com.marlonreina.discord.api.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscordUserResponse {

    private String id;
    private String username;
    private String avatar;
    private String email;

    @JsonProperty("global_name")
    private String globalName;

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

    public String getGlobalName() {
        return globalName;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setGlobalName(String globalName) {
        this.globalName = globalName;
    }
}