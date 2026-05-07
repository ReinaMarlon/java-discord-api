package com.marlonreina.discord.api.domain.model;

import java.util.List;

public class GuildConfigAggregate {

    private final String guildId;
    private final String guildName;
    private final String prefix;
    private final boolean premium;
    private final GuildFeatures features;
    private final WelcomeConfig welcome;
    private final LogConfig logs;
    private final AdsConfig ads;
    private final List<GuildCommandConfig> commands;

    public GuildConfigAggregate(
            String guildId,
            String guildName,
            String prefix,
            boolean premium,
            GuildFeatures features,
            WelcomeConfig welcome,
            LogConfig logs,
            AdsConfig ads,
            List<GuildCommandConfig> commands
    ) {
        this.guildId = guildId;
        this.guildName = guildName;
        this.prefix = prefix;
        this.premium = premium;
        this.features = features;
        this.welcome = welcome;
        this.logs = logs;
        this.ads = ads;
        this.commands = List.copyOf(commands);
    }

    public String getGuildId() {
        return guildId;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isPremium() {
        return premium;
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

    public List<GuildCommandConfig> getCommands() {
        return commands;
    }
}
