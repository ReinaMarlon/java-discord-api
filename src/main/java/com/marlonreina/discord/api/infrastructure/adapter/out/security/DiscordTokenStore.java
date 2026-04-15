package com.marlonreina.discord.api.infrastructure.adapter.out.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DiscordTokenStore {

    private final Map<String, String> tokens = new HashMap<>();

    public void save(String discordId, String accessToken) {
        tokens.put(discordId, accessToken);
    }

    public String get(String discordId) {
        return tokens.get(discordId);
    }
}
