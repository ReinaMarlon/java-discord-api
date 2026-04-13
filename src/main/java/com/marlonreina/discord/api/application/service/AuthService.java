package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import com.marlonreina.discord.api.domain.port.out.DiscordOAuthPort;
import com.marlonreina.discord.api.domain.port.out.JwtPort;
import com.marlonreina.discord.api.shared.dto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final DiscordOAuthPort discordOAuthPort;
    private final JwtPort jwtPort;

    public AuthService(DiscordOAuthPort discordOAuthPort, JwtPort jwtPort) {
        this.discordOAuthPort = discordOAuthPort;
        this.jwtPort = jwtPort;
    }

    @Override
    public AuthResponse authenticate(String code) {
        String accessToken = discordOAuthPort.getAccessToken(code);
        User user = discordOAuthPort.getUser(accessToken);
        String token = jwtPort.generateToken(user);

        return new AuthResponse(token, user);
    }
}