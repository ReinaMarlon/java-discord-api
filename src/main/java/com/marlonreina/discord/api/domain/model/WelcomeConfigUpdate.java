package com.marlonreina.discord.api.domain.model;

public class WelcomeConfigUpdate {

    private final String guildId;
    private final boolean enabled;
    private final String channelId;
    private final String message;
    private final String embedJson;

    public WelcomeConfigUpdate(
            String guildId,
            boolean enabled,
            String channelId,
            String message,
            String embedJson
    ) {
        this.guildId = guildId;
        this.enabled = enabled;
        this.channelId = channelId;
        this.message = message;
        this.embedJson = embedJson;
    }

    public String getGuildId() {
        return guildId;
    }

    public boolean isEnabled() {
        return enabled;
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
}
