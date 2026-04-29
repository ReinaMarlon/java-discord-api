package com.marlonreina.discord.api.shared.dto;

import java.util.Collections;
import java.util.List;

public class UserResponse {

    private final String id;
    private final String username;
    private final String avatar;
    private final List<GuildResponse> guilds;

    public UserResponse(String id, String username, String avatar, List<GuildResponse> guilds) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.guilds = List.copyOf(guilds);
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
