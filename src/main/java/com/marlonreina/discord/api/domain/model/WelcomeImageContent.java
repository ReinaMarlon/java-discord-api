package com.marlonreina.discord.api.domain.model;

public class WelcomeImageContent {

    private final String imageName;
    private final byte[] imageData;
    private final String mimeType;

    public WelcomeImageContent(String imageName, byte[] imageData, String mimeType) {
        this.imageName = imageName;
        this.imageData = imageData.clone();
        this.mimeType = mimeType;
    }

    public String getImageName() {
        return imageName;
    }

    public byte[] getImageData() {
        return imageData.clone();
    }

    public String getMimeType() {
        return mimeType;
    }
}
