package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import com.marlonreina.discord.api.domain.port.out.DiscordOauthPort;
import com.marlonreina.discord.api.domain.port.out.JwtPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.security.DiscordTokenStore;
import com.marlonreina.discord.api.shared.dto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final DiscordOauthPort discordOauthPort;
    private final JwtPort jwtPort;
    private final DiscordTokenStore tokenStore;

    public AuthService(DiscordOauthPort discordOauthPort, JwtPort jwtPort, DiscordTokenStore tokenStore) {
        this.discordOauthPort = discordOauthPort;
        this.jwtPort = jwtPort;
        this.tokenStore = tokenStore;
    }

    @Override
    public String authenticate(String code) {
        String accessToken = discordOauthPort.getAccessToken(code);
        User user = discordOauthPort.getUser(accessToken);
        tokenStore.save(user.getId(), accessToken);
        return jwtPort.generateToken(user);
    }
}