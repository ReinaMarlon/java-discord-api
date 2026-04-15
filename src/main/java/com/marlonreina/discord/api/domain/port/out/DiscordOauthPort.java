package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.shared.dto.DiscordTokenResponse;

public interface DiscordOauthPort {
    User getUser(String accessToken);

    DiscordTokenResponse getToken(String code);
}