package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import com.marlonreina.discord.api.domain.port.out.DiscordOauthPort;
import com.marlonreina.discord.api.domain.port.out.JwtPort;
import com.marlonreina.discord.api.domain.port.out.UserRepositoryPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.security.DiscordTokenStore;
import com.marlonreina.discord.api.shared.dto.DiscordTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService implements AuthUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final DiscordOauthPort discordOauthPort;
    private final JwtPort jwtPort;
    private final DiscordTokenStore tokenStore;
    private final UserRepositoryPort userRepository;

    public AuthService(DiscordOauthPort discordOauthPort, JwtPort jwtPort,
                       DiscordTokenStore tokenStore, UserRepositoryPort userRepository) {
        this.discordOauthPort = discordOauthPort;
        this.jwtPort = jwtPort;
        this.tokenStore = tokenStore;
        this.userRepository = userRepository;
    }

    public boolean isTokenExpired(User user) {
        return user.getExpiresAt().isBefore(LocalDateTime.now().minusMinutes(1));
    }

    @Override
    public String authenticate(String code) {

        DiscordTokenResponse tokenResponse = discordOauthPort.getToken(code);

        User user = discordOauthPort.getUser(tokenResponse.getAccessToken());
        if (user.getId() == null) {
            throw new RuntimeException("DISCORD_ID_NULL");
        }
        user.setAccessToken(tokenResponse.getAccessToken());
        user.setRefreshToken(tokenResponse.getRefreshToken());
        user.setExpiresAt(
                LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn())
        );
        LOGGER.debug("Authenticated Discord user {}", user.getId());
        userRepository.save(user);

        tokenStore.save(user.getId(), tokenResponse.getAccessToken());

        return jwtPort.generateToken(user);
    }
}
