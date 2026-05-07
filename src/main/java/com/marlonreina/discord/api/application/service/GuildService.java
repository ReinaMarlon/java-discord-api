package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.application.dto.request.WelcomeConfigRequest;
import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;
import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfigUpdate;
import com.marlonreina.discord.api.domain.model.WelcomeImage;
import com.marlonreina.discord.api.domain.model.WelcomeImageContent;
import com.marlonreina.discord.api.domain.model.WelcomeImageUpdate;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
public class GuildService {

    private final GuildConfigRepositoryPort guildConfigRepository;
    private final DiscordPort discordPort;
    private final WelcomeImageStorageService welcomeImageStorageService;
    private static final long ADMINISTRATOR_PERMISSION = 0x8L;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "ObjectMapper is a Spring-managed singleton used only for serialization"
    )
    private final ObjectMapper objectMapper;

    public GuildService(
            GuildConfigRepositoryPort guildConfigRepository,
            DiscordPort discordPort,
            ObjectMapper objectMapper,
            WelcomeImageStorageService welcomeImageStorageService
    ) {
        this.guildConfigRepository = guildConfigRepository;
        this.discordPort = discordPort;
        this.objectMapper = objectMapper;
        this.welcomeImageStorageService = welcomeImageStorageService;
    }

    public void assertAccess(String userId, String guildId) {
        findManageableGuild(userId, guildId);
    }

    public GuildConfigAggregate getFullConfig(String guildId, String userId) {
        DiscordGuild guild = findManageableGuild(userId, guildId);

        return guildConfigRepository.findOrCreateByGuildId(guildId, guild.getName());
    }

    public GuildFullDataResponse getFullGuildData(String guildId, String userId) {
        GuildConfigAggregate config = getFullConfig(guildId, userId);

        List<DiscordChannel> channels = discordPort.getGuildChannels(guildId);
        List<DiscordRole> roles = discordPort.getGuildRoles(guildId);

        return new GuildFullDataResponse(config, channels, roles);
    }

    public WelcomeConfig updateWelcomeConfig(
            String guildId,
            String userId,
            WelcomeConfigRequest request
    ) {
        assertAccess(userId, guildId);
        validateWelcomeRequest(guildId, request);

        WelcomeConfigUpdate update = new WelcomeConfigUpdate(
                guildId,
                Boolean.TRUE.equals(request.getEnabled()),
                request.getChannelId(),
                extractRawMessage(request.getTemplate()),
                serializePayload(request)
        );

        return guildConfigRepository.saveWelcomeConfig(update);
    }

    public WelcomeImage updateWelcomeImage(
            String guildId,
            String userId,
            MultipartFile image
    ) {
        assertAccess(userId, guildId);

        WelcomeImageUpdate update = welcomeImageStorageService.store(guildId, image);
        return guildConfigRepository.saveWelcomeImage(update);
    }

    public Optional<WelcomeImageContent> getWelcomeImage(String guildId) {
        return guildConfigRepository.findWelcomeImageContentByGuildId(guildId)
                .filter(image -> image.getImageData().length > 0);
    }

    private DiscordGuild findManageableGuild(String userId, String guildId) {
        return discordPort.getUserGuilds(userId)
                .stream()
                .filter(guild -> guild.getId().equals(guildId))
                .filter(guild -> guild.isOwner() || isAdmin(guild))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("GUILD_ACCESS_DENIED"));
    }

    private boolean isAdmin(DiscordGuild guild) {
        return (guild.getPermissions() & ADMINISTRATOR_PERMISSION) == ADMINISTRATOR_PERMISSION;
    }

    private void validateWelcomeRequest(String guildId, WelcomeConfigRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("WELCOME_PAYLOAD_REQUIRED");
        }
        if (request.getGuildId() != null && !guildId.equals(request.getGuildId())) {
            throw new IllegalArgumentException("GUILD_ID_MISMATCH");
        }
        if (request.getModule() != null && !"welcome".equals(request.getModule())) {
            throw new IllegalArgumentException("WELCOME_MODULE_REQUIRED");
        }
    }

    private String extractRawMessage(JsonNode template) {
        if (template == null || template.get("raw") == null || template.get("raw").isNull()) {
            return null;
        }

        return template.get("raw").asText();
    }

    private String serializePayload(WelcomeConfigRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JacksonException e) {
            throw new RuntimeException("WELCOME_PAYLOAD_INVALID", e);
        }
    }

}
