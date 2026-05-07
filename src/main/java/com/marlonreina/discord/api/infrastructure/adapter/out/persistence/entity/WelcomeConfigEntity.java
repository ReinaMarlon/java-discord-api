package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "welcome_config")
public class WelcomeConfigEntity {

    @Id
    @Column(name = "guild_id")
    private String guildId;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "channel_id")
    private String channelId;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "embed_json", columnDefinition = "TEXT")
    private String embedJson;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmbedJson() {
        return embedJson;
    }

    public void setEmbedJson(String embedJson) {
        this.embedJson = embedJson;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
