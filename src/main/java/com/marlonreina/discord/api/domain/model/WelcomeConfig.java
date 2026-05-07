package com.marlonreina.discord.api.domain.model;

public class WelcomeConfig {

    private final String channelId;
    private final String message;
    private final String embedJson;
    private final boolean enabled;

    public WelcomeConfig(String channelId, String message, String embedJson, boolean enabled) {
        this.channelId = channelId;
        this.message = message;
        this.embedJson = embedJson;
        this.enabled = enabled;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getMessage() {
        return message;
    }

    public String getEmbedJson() {
        return embedJson;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
