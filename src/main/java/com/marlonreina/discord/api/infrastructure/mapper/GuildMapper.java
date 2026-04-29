package com.marlonreina.discord.api.infrastructure.mapper;

import com.marlonreina.discord.api.application.dto.response.GuildConfigResponse;
import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;

import java.util.List;

public class GuildMapper {

    public static GuildConfigResponse toResponse(
            GuildConfigAggregate agg,
            List<DiscordRole> roles,
            List<DiscordChannel> channels
    ) {
        return new GuildConfigResponse(
                agg.getGuildId(),
                agg.getPrefix(),
                roles,
                channels
        );
    }
}