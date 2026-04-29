package com.marlonreina.discord.api.domain.model;

public class GuildFeatures {

    private final boolean welcomeEnabled;
    private final boolean economyEnabled;
    private final boolean premium;

    public GuildFeatures(boolean welcomeEnabled, boolean economyEnabled, boolean premium) {
        this.welcomeEnabled = welcomeEnabled;
        this.economyEnabled = economyEnabled;
        this.premium = premium;
    }

    public boolean isWelcomeEnabled() {
        return welcomeEnabled;
    }

    public boolean isEconomyEnabled() {
        return economyEnabled;
    }

    public boolean isPremium() {
        return premium;
    }
}
