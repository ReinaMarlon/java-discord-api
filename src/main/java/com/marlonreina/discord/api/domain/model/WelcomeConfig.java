package com.marlonreina.discord.api.domain.model;

public class WelcomeConfig {

    private final String channelId;
    private final String message;

    public WelcomeConfig(String channelId, String message) {
        this.channelId = channelId;
        this.message = message;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getMessage() {
        return message;
    }
}
