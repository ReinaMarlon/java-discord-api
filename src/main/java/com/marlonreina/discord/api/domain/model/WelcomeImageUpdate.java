package com.marlonreina.discord.api.domain.model;

public class WelcomeImageUpdate {

    private final String guildId;
    private final String imageUrl;
    private final String imageHash;
    private final String mimeType;
    private final int width;
    private final int height;

    public WelcomeImageUpdate(
            String guildId,
            String imageUrl,
            String imageHash,
            String mimeType,
            int width,
            int height
    ) {
        this.guildId = guildId;
        this.imageUrl = imageUrl;
        this.imageHash = imageHash;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getImageUrl() {
        return imageUrl;
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
}
