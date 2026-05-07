package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfigUpdate;

public interface GuildConfigRepositoryPort {

    GuildConfigAggregate findOrCreateByGuildId(String guildId, String guildName);

    WelcomeConfig saveWelcomeConfig(WelcomeConfigUpdate update);
}
