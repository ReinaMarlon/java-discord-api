package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

public class WelcomeImageEntity {

    private static final byte[] EMPTY_IMAGE_DATA = new byte[0];

    private String guildId;
    private String imageUrl;
    private String imageName;
    private byte[] imageData = EMPTY_IMAGE_DATA;
    private String imageHash;
    private String mimeType;
    private int width;
    private int height;
    private LocalDateTime updatedAt;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageData() {
        return imageData.clone();
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData == null ? EMPTY_IMAGE_DATA : imageData.clone();
    }

    public String getImageHash() {
        return imageHash;
    }

    public void setImageHash(String imageHash) {
        this.imageHash = imageHash;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
