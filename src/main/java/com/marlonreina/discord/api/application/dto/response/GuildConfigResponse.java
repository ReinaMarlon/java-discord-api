package com.marlonreina.discord.api.application.dto.response;

import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordRole;

import java.util.List;

public class GuildConfigResponse {

    private final String guildId;
    private final String prefix;
    private List<DiscordRole> roles;
    private List<DiscordChannel> channels;

    public GuildConfigResponse(
            String guildId,
            String prefix,
            List<DiscordRole> roles,
            List<DiscordChannel> channels
    ) {
        this.guildId = guildId;
        this.prefix = prefix;
        this.roles = List.copyOf(roles);
        this.channels = List.copyOf(channels);
    }

    public String getGuildId() {
        return guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<DiscordRole> getRoles() {
        return roles;
    }

    public void setRoles(List<DiscordRole> roles) {
        this.roles = List.copyOf(roles);
    }

    public List<DiscordChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<DiscordChannel> channels) {
        this.channels = List.copyOf(channels);
    }
}
