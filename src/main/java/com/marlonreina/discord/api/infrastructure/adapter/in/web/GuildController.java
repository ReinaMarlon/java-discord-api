package com.marlonreina.discord.api.infrastructure.adapter.in.web;

import com.marlonreina.discord.api.application.dto.request.WelcomeConfigRequest;
import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;
import com.marlonreina.discord.api.application.service.GuildService;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/guilds")
public class GuildController {

    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @GetMapping("/{guildId}/config")
    public GuildFullDataResponse getFullGuild(
            @PathVariable String guildId,
            Authentication auth
    ) {
        String userId = auth.getName();
        return guildService.getFullGuildData(guildId, userId);
    }

    @PutMapping("/{guildId}/config/welcome")
    public ResponseEntity<WelcomeConfig> updateWelcomeConfig(
            @PathVariable String guildId,
            @RequestBody WelcomeConfigRequest request,
            Authentication auth
    ) {
        String userId = auth.getName();
        WelcomeConfig welcome = guildService.updateWelcomeConfig(guildId, userId, request);

        return ResponseEntity.ok(welcome);
    }
}
