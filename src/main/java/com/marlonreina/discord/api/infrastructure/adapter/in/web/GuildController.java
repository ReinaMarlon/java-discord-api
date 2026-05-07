package com.marlonreina.discord.api.infrastructure.adapter.in.web;

import com.marlonreina.discord.api.application.dto.request.WelcomeConfigRequest;
import com.marlonreina.discord.api.application.dto.response.GuildFullDataResponse;
import com.marlonreina.discord.api.application.service.GuildService;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.model.WelcomeImage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(
            value = "/{guildId}/config/welcome/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<WelcomeImage> updateWelcomeImage(
            @PathVariable String guildId,
            @RequestParam("image") MultipartFile image,
            Authentication auth
    ) {
        String userId = auth.getName();
        WelcomeImage welcomeImage = guildService.updateWelcomeImage(guildId, userId, image);

        return ResponseEntity.ok(welcomeImage);
    }

    @GetMapping("/{guildId}/config/welcome/image")
    public ResponseEntity<byte[]> getWelcomeImage(@PathVariable String guildId) {
        return getWelcomeImageContent(guildId);
    }

    @GetMapping("/{guildId}/config/welcome/image/raw")
    public ResponseEntity<byte[]> getWelcomeImageRaw(@PathVariable String guildId) {
        return getWelcomeImageContent(guildId);
    }

    private ResponseEntity<byte[]> getWelcomeImageContent(String guildId) {
        return guildService.getWelcomeImage(guildId)
                .map(image -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(image.getMimeType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition(image.getImageName()))
                        .body(image.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private String contentDisposition(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return "inline";
        }

        return "inline; filename=\"" + imageName.replace("\"", "") + "\"";
    }
}
