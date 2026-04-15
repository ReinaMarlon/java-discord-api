package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordUser;

import java.util.List;

public interface DiscordPort {

    DiscordUser getUser(String discordId);

    List<DiscordGuild> getUserGuilds(String discordId);
}