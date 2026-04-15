package com.marlonreina.discord.api.infrastructure.adapter.out.security;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.out.UserRepositoryPort;
import com.marlonreina.discord.api.shared.dto.DiscordTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class DiscordTokenService {

    private final UserRepositoryPort userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.discord.client-id}")
    private String clientId;

    @Value("${spring.discord.client-secret}")
    private String clientSecret;

    public DiscordTokenService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public String getValidAccessToken(String discordId) {
        User user = userRepository.findById(discordId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (user.getExpiresAt() != null
                && user.getExpiresAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            return user.getAccessToken();
        }

        return refreshToken(user);
    }

    private String refreshToken(User user) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", user.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<DiscordTokenResponse> response =
                restTemplate.exchange(
                        "https://discord.com/api/oauth2/token",
                        HttpMethod.POST,
                        request,
                        DiscordTokenResponse.class
                );

        DiscordTokenResponse token = response.getBody();

        user.setAccessToken(token.getAccessToken());
        user.setRefreshToken(token.getRefreshToken());
        user.setExpiresAt(LocalDateTime.now().plusSeconds(token.getExpiresIn()));

        userRepository.save(user);

        return token.getAccessToken();
    }
}