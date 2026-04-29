package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.DiscordGuild;
import java.util.List;

public interface DiscordGateway {
    List<DiscordGuild> getUserGuilds(String userId);
}