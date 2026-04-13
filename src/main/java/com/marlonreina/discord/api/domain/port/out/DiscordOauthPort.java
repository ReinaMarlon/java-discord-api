package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.User;

public interface DiscordOauthPort {
    String getAccessToken(String code);

    User getUser(String accessToken);
}