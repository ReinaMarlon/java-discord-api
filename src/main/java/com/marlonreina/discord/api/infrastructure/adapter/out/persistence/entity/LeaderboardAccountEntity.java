package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leaderboard_accounts")
public class LeaderboardAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guild_id", nullable = false)
    private String guildId;

    @Column(name = "discord_id", nullable = false)
    private String discordId;

    @Column(name = "riot_name")
    private String riotName;

    @Column(name = "riot_tag")
    private String riotTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getRiotName() {
        return riotName;
    }

    public void setRiotName(String riotName) {
        this.riotName = riotName;
    }

    public String getRiotTag() {
        return riotTag;
    }

    public void setRiotTag(String riotTag) {
        this.riotTag = riotTag;
    }
}
