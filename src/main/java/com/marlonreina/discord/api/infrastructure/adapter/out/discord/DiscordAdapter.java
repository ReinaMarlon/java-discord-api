package com.marlonreina.discord.api.infrastructure.adapter.out.discord;

import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordMember;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.DiscordUser;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.security.DiscordTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DiscordAdapter implements DiscordPort {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_RESPONSE =
            new ParameterizedTypeReference<>() {
            };
    private static final ParameterizedTypeReference<List<Map<String, Object>>> LIST_OF_MAPS_RESPONSE =
            new ParameterizedTypeReference<>() {
            };
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BOT_AUTH_PREFIX = "Bot ";

    private final RestTemplate restTemplate = new RestTemplate();
    private final DiscordTokenService tokenService;

    private final Map<String, List<DiscordGuild>> guildCache = new ConcurrentHashMap<>();
    private volatile List<String> botGuildIds;

    @Value("${discord.bot-token}")
    private String botToken;

    public DiscordAdapter(DiscordTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public DiscordUser getUser(String discordId) {

        String token = tokenService.getValidAccessToken(discordId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "https://discord.com/api/users/@me",
                HttpMethod.GET,
                entity,
                MAP_RESPONSE
        );

        Map<String, Object> body = response.getBody();
        if (body == null) {
            throw new RuntimeException("DISCORD_USER_NULL");
        }

        return new DiscordUser(
                (String) body.get("id"),
                (String) body.get("username"),
                (String) body.get("avatar")
        );
    }

    @Override
    public List<DiscordGuild> getUserGuilds(String discordId) {

        if (guildCache.containsKey(discordId)) {
            return guildCache.get(discordId);
        }

        String token = tokenService.getValidAccessToken(discordId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://discord.com/api/users/@me/guilds",
                HttpMethod.GET,
                entity,
                LIST_OF_MAPS_RESPONSE
        );

        List<Map<String, Object>> guilds = response.getBody();
        if (guilds == null) {
            return List.of();
        }

        List<DiscordGuild> result = guilds.stream().map(g -> new DiscordGuild(
                (String) g.get("id"),
                (String) g.get("name"),
                (String) g.get("icon"),
                (String) g.get("banner"),
                (Boolean) g.get("owner"),
                Long.parseLong(g.get("permissions").toString())
        )).toList();

        guildCache.put(discordId, result);

        return result;
    }

    @Override
    public boolean isBotPresentInGuild(String guildId) {
        return getBotGuildIds().contains(guildId);
    }

    private List<String> getBotGuildIds() {
        if (botGuildIds != null) {
            return botGuildIds;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BOT_AUTH_PREFIX + botToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://discord.com/api/users/@me/guilds",
                HttpMethod.GET,
                entity,
                LIST_OF_MAPS_RESPONSE
        );

        List<Map<String, Object>> guilds = response.getBody();
        if (guilds == null) {
            botGuildIds = List.of();
            return botGuildIds;
        }

        botGuildIds = guilds.stream()
                .map(guild -> (String) guild.get("id"))
                .toList();

        return botGuildIds;
    }

    @Override
    public List<DiscordChannel> getGuildChannels(String guildId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BOT_AUTH_PREFIX + botToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://discord.com/api/guilds/" + guildId + "/channels",
                HttpMethod.GET,
                entity,
                LIST_OF_MAPS_RESPONSE
        );

        List<Map<String, Object>> channels = response.getBody();

        if (channels == null) {
            return List.of();
        }

        return channels.stream().map(c -> new DiscordChannel(
                (String) c.get("id"),
                (String) c.get("name"),
                (Integer) c.get("type")
        )).toList();
    }

    @Override
    public List<DiscordRole> getGuildRoles(String guildId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BOT_AUTH_PREFIX + botToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://discord.com/api/guilds/" + guildId + "/roles",
                HttpMethod.GET,
                entity,
                LIST_OF_MAPS_RESPONSE
        );

        List<Map<String, Object>> roles = response.getBody();

        if (roles == null) {
            return List.of();
        }

        return roles.stream().map(r -> new DiscordRole(
                (String) r.get("id"),
                (String) r.get("name")
        )).toList();
    }

    @Override
    public List<DiscordMember> getGuildMembers(String guildId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BOT_AUTH_PREFIX + botToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://discord.com/api/guilds/" + guildId + "/members?limit=1000",
                HttpMethod.GET,
                entity,
                LIST_OF_MAPS_RESPONSE
        );

        List<Map<String, Object>> members = response.getBody();

        if (members == null) {
            return List.of();
        }

        return members.stream().map(member -> {
            Object userValue = member.get("user");
            if (!(userValue instanceof Map<?, ?> user)) {
                throw new RuntimeException("DISCORD_MEMBER_USER_NULL");
            }

            return new DiscordMember(
                    (String) user.get("id"),
                    (String) user.get("username")
            );
        }).toList();
    }
}
