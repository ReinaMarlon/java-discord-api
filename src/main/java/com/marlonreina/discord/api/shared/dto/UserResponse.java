package com.marlonreina.discord.api.shared.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class UserResponse {

    private String id;
    private String username;
    private String avatar;
    private List<GuildResponse> guilds;

    public UserResponse(String id, String username, String avatar, List<GuildResponse> guilds) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.guilds = new ArrayList<>(guilds);
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

    public List<GuildResponse> getGuilds() {
        return Collections.unmodifiableList(guilds);
    }
}