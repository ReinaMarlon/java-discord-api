package com.marlonreina.discord.api.domain.model;

public class LogConfig {

    private final boolean enabled;
    private final String channelId;

    public LogConfig(boolean enabled, String channelId) {
        this.enabled = enabled;
        this.channelId = channelId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getChannelId() {
        return channelId;
    }
}
