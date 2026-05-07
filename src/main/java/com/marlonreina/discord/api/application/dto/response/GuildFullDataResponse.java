package com.marlonreina.discord.api.application.dto.response;

import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;

import java.util.List;

public class GuildFullDataResponse {

    private final GuildConfigAggregate config;
    private final List<DiscordChannel> channels;
    private final List<DiscordRole> roles;

    public GuildFullDataResponse(GuildConfigAggregate config,
                                 List<DiscordChannel> channels,
                                 List<DiscordRole> roles) {
        this.config = config;
        this.channels = List.copyOf(channels);
        this.roles = List.copyOf(roles);
    }

    public GuildConfigAggregate getConfig() {
        return config;
    }

    public List<DiscordChannel> getChannels() {
        return channels;
    }

    public List<DiscordRole> getRoles() {
        return roles;
    }
}
