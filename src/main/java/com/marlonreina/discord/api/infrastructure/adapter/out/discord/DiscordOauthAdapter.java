package com.marlonreina.discord.api.infrastructure.adapter.out.discord;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.out.DiscordOauthPort;
import com.marlonreina.discord.api.shared.dto.DiscordTokenResponse;
import com.marlonreina.discord.api.shared.dto.DiscordUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Component
public class DiscordOauthAdapter implements DiscordOauthPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.discord.client-id}")
    private String clientId;

    @Value("${spring.discord.client-secret}")
    private String clientSecret;

    @Value("${spring.discord.redirect-uri}")
    private String redirectUri;

    @Override
    public DiscordTokenResponse getToken(String code) {
        try {

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
                            "https://discord.com/api/oauth2/token",
                            HttpMethod.POST,
                            request,
                            DiscordTokenResponse.class
                    );

            DiscordTokenResponse token = response.getBody();

            if (token == null) {
                throw new RuntimeException("DISCORD_TOKEN_NULL");
            }

            return token;

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 400) {
                throw new RuntimeException("OAUTH_CODE_INVALID");
            }

            throw new RuntimeException("DISCORD_OAUTH_ERROR");
        }
    }

    @Override
    public User getUser(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url = "https://discord.com/api/users/@me";

        ResponseEntity<DiscordUserResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        DiscordUserResponse.class
                );

        DiscordUserResponse discordUser = response.getBody();
        // System.out.println("DISCORD ID: " + discordUser.getId());

        return new User(
                discordUser.getId(),
                discordUser.getUsername(),
                discordUser.getAvatar(),
                discordUser.getEmail()
        );
    }
}