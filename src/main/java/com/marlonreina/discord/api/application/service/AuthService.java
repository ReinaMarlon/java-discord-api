package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import com.marlonreina.discord.api.domain.port.out.DiscordOauthPort;
import com.marlonreina.discord.api.domain.port.out.JwtPort;
import com.marlonreina.discord.api.shared.dto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final DiscordOauthPort discordOauthPort;
    private final JwtPort jwtPort;

    public AuthService(DiscordOauthPort discordOauthPort, JwtPort jwtPort) {
        this.discordOauthPort = discordOauthPort;
        this.jwtPort = jwtPort;
    }

    @Override
    public String authenticate(String code) {
        String accessToken = discordOauthPort.getAccessToken(code);
        User user = discordOauthPort.getUser(accessToken);
        return jwtPort.generateToken(user);
    }
}