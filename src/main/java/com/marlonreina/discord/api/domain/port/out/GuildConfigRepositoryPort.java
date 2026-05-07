package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfigUpdate;
import com.marlonreina.discord.api.domain.model.WelcomeImage;
import com.marlonreina.discord.api.domain.model.WelcomeImageContent;
import com.marlonreina.discord.api.domain.model.WelcomeImageUpdate;

import java.util.Optional;

public interface GuildConfigRepositoryPort {

    GuildConfigAggregate findOrCreateByGuildId(String guildId, String guildName);

    WelcomeConfig saveWelcomeConfig(WelcomeConfigUpdate update);

    WelcomeImage saveWelcomeImage(WelcomeImageUpdate update);

    Optional<WelcomeImageContent> findWelcomeImageContentByGuildId(String guildId);
}
