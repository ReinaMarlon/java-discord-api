package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;
import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
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
        findAccessibleGuild(userId, guildId);
    }

    public GuildConfigAggregate getFullConfig(String guildId, String userId) {
        DiscordGuild guild = findAccessibleGuild(userId, guildId);

        return guildConfigRepository.findOrCreateByGuildId(guildId, guild.getName());
    }

    public GuildFullDataResponse getFullGuildData(String guildId, String userId) {
        GuildConfigAggregate config = getFullConfig(guildId, userId);

        List<DiscordChannel> channels = discordPort.getGuildChannels(guildId);
        List<DiscordRole> roles = discordPort.getGuildRoles(guildId);

        return new GuildFullDataResponse(config, channels, roles);
    }

    private DiscordGuild findAccessibleGuild(String userId, String guildId) {
        return discordPort.getUserGuilds(userId)
                .stream()
                .filter(guild -> guild.getId().equals(guildId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No access"));
    }
}
