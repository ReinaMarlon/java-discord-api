package com.marlonreina.discord.api.domain.model;

public class AdsConfig {

    private final boolean enabled;
    private final Long channelId;

    public AdsConfig(boolean enabled, Long channelId) {
        this.enabled = enabled;
        this.channelId = channelId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Long getChannelId() {
        return channelId;
    }
}
