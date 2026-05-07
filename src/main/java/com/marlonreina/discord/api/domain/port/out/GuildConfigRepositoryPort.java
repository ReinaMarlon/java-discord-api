package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;

public interface GuildConfigRepositoryPort {

    GuildConfigAggregate findOrCreateByGuildId(String guildId, String guildName);
}
