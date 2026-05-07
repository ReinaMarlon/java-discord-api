package com.marlonreina.discord.api.infrastructure.mapper;

import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;

public class GuildFullDataMapper {

    public static GuildFullDataResponse toResponse(GuildFullDataResponse data) {
        return new GuildFullDataResponse(
                data.getConfig(),
                data.getChannels(),
                data.getRoles()
        );
    }
}
