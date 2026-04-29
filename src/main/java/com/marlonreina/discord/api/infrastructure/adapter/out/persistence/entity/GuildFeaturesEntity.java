package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guild_features")
public class GuildFeaturesEntity {

    @Id
    @Column(name = "guild_id")
    private String guildId;

    private boolean premium;

    @Column(name = "welcome_enabled")
    private boolean welcomeEnabled;

    @Column(name = "advanced_logs")
    private boolean advancedLogs;

    @Column(name = "economy_enabled")
    private boolean economyEnabled;

    @Column(name = "custom_commands")
    private boolean customCommands;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isWelcomeEnabled() {
        return welcomeEnabled;
    }

    public void setWelcomeEnabled(boolean welcomeEnabled) {
        this.welcomeEnabled = welcomeEnabled;
    }

    public boolean isAdvancedLogs() {
        return advancedLogs;
    }

    public void setAdvancedLogs(boolean advancedLogs) {
        this.advancedLogs = advancedLogs;
    }

    public boolean isEconomyEnabled() {
        return economyEnabled;
    }

    public void setEconomyEnabled(boolean economyEnabled) {
        this.economyEnabled = economyEnabled;
    }

    public boolean isCustomCommands() {
        return customCommands;
    }

    public void setCustomCommands(boolean customCommands) {
        this.customCommands = customCommands;
    }
}
