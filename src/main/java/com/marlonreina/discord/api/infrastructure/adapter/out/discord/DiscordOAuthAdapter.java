package com.marlonreina.discord.api.infrastructure.adapter.out.discord;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.out.DiscordOAuthPort;
import com.marlonreina.discord.api.shared.dto.DiscordTokenResponse;
import com.marlonreina.discord.api.shared.dto.DiscordUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class DiscordOAuthAdapter implements DiscordOAuthPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.discord.client-id}")
    private String clientId;

    @Value("${spring.discord.client-secret}")
    private String clientSecret;

    @Value("${spring.discord.redirect-uri}")
    private String redirectUri;

    @Override
    public String getAccessToken(String code) {

        String url = "https://discord.com/api/oauth2/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<DiscordTokenResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        DiscordTokenResponse.class
                );

        if (response.getBody() == null) {
            throw new RuntimeException("Error obteniendo access token de Discord");
        }

        return response.getBody().getAccessToken();
    }

    @Override
    public User getUser(String accessToken) {

        String url = "https://discord.com/api/users/@me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<DiscordUserResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        DiscordUserResponse.class
                );

        DiscordUserResponse discordUser = response.getBody();

        if (discordUser == null) {
            throw new RuntimeException("Error obteniendo usuario de Discord");
        }

        return new User(
                discordUser.getId(),
                discordUser.getUsername(),
                discordUser.getAvatar()
        );
    }
}