package com.marlonreina.discord.api.domain.model;

import java.util.List;

public class GuildFullResponse {

    private final GuildConfigAggregate config;
    private final List<DiscordChannel> channels;
    private final List<DiscordRole> roles;
    private final List<DiscordMember> members;

    public GuildFullResponse(
            GuildConfigAggregate config,
            List<DiscordChannel> channels,
            List<DiscordRole> roles,
            List<DiscordMember> members
    ) {
        this.config = config;
        this.channels = List.copyOf(channels);
        this.roles = List.copyOf(roles);
        this.members = List.copyOf(members);
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

    public List<DiscordMember> getMembers() {
        return members;
    }
}
