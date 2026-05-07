package com.marlonreina.discord.api.domain.model;

import java.time.LocalDateTime;

public class WelcomeImage {

    private final String guildId;
    private final String imageUrl;
    private final String imageName;
    private final String imageHash;
    private final String mimeType;
    private final int width;
    private final int height;
    private final LocalDateTime updatedAt;

    public WelcomeImage(
            String guildId,
            String imageUrl,
            String imageName,
            String imageHash,
            String mimeType,
            int width,
            int height,
            LocalDateTime updatedAt
    ) {
        this.guildId = guildId;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.imageHash = imageHash;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.updatedAt = updatedAt;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageHash() {
        return imageHash;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
