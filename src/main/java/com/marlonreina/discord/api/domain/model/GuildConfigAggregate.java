package com.marlonreina.discord.api.domain.model;

public class GuildConfigAggregate {

    private final String guildId;
    private final String prefix;
    private final GuildFeatures features;
    private final WelcomeConfig welcome;
    private final LogConfig logs;
    private final AdsConfig ads;

    public GuildConfigAggregate(String guildId, String prefix, GuildFeatures features,
                                WelcomeConfig welcome, LogConfig logs, AdsConfig ads) {
        this.guildId = guildId;
        this.prefix = prefix;
        this.features = features;
        this.welcome = welcome;
        this.logs = logs;
        this.ads = ads;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public GuildFeatures getFeatures() {
        return features;
    }

    public WelcomeConfig getWelcome() {
        return welcome;
    }

    public LogConfig getLogs() {
        return logs;
    }

    public AdsConfig getAds() {
        return ads;
    }
}
