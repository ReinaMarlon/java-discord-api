package com.marlonreina.discord.api.application.dto.response;

import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordMember;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;

import java.util.List;

public class GuildFullDataResponse {

    private final GuildConfigAggregate config;
    private final List<DiscordChannel> channels;
    private final List<DiscordRole> roles;
    private final List<DiscordMember> members;

    public GuildFullDataResponse(GuildConfigAggregate config,
                                 List<DiscordChannel> channels,
                                 List<DiscordRole> roles,
                                 List<DiscordMember> members) {
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
