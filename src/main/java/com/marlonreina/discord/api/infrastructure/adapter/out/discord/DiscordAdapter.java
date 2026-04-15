package com.marlonreina.discord.api.infrastructure.adapter.out.discord;

import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordUser;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.security.DiscordTokenStore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class DiscordAdapter implements DiscordPort {

    private final DiscordTokenStore tokenStore;
    private final RestTemplate restTemplate = new RestTemplate();

    public DiscordAdapter(DiscordTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public DiscordUser getUser(String discordId) {

        String token = tokenStore.get(discordId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://discord.com/api/users/@me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = response.getBody();

        return new DiscordUser(
                (String) body.get("id"),
                (String) body.get("username"),
                (String) body.get("avatar")
        );
    }

    @Override
    public List<DiscordGuild> getUserGuilds(String discordId) {

        String token = tokenStore.get(discordId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                "https://discord.com/api/users/@me/guilds",
                HttpMethod.GET,
                entity,
                List.class
        );

        List<Map<String, Object>> guilds = response.getBody();

        return guilds.stream().map(g -> new DiscordGuild(
                (String) g.get("id"),
                (String) g.get("name"),
                (String) g.get("icon"),
                (String) g.get("banner"),
                (Boolean) g.get("owner"),
                Long.parseLong(g.get("permissions").toString())
        )).toList();
    }
}
