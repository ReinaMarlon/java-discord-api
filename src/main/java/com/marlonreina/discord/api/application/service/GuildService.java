package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;
import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordMember;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    private final GuildConfigRepositoryPort guildConfigRepository;
    private final DiscordPort discordPort;

    public GuildService(
            GuildConfigRepositoryPort guildConfigRepository,
            DiscordPort discordPort
    ) {
        this.guildConfigRepository = guildConfigRepository;
        this.discordPort = discordPort;
    }

    public void assertAccess(String userId, String guildId) {
        boolean hasAccess = discordPort.getUserGuilds(userId)
                .stream()
                .anyMatch(g -> g.getId().equals(guildId));

        if (!hasAccess) {
            throw new RuntimeException("No access");
        }
    }

    public GuildConfigAggregate getFullConfig(String guildId, String userId) {
        assertAccess(userId, guildId);

        return guildConfigRepository.findOrCreateByGuildId(guildId);
    }

    public GuildFullDataResponse getFullGuildData(String guildId, String userId) {
        GuildConfigAggregate config = getFullConfig(guildId, userId);

        List<DiscordChannel> channels = discordPort.getGuildChannels(guildId);
        List<DiscordRole> roles = discordPort.getGuildRoles(guildId);
        List<DiscordMember> members = discordPort.getGuildMembers(guildId);

        return new GuildFullDataResponse(config, channels, roles, members);
    }
}
