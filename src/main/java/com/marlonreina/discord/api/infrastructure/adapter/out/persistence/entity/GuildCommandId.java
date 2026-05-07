package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

public class GuildCommandId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String guildId;
    private Long commandId;

    public GuildCommandId() {
    }

    public GuildCommandId(String guildId, Long commandId) {
        this.guildId = guildId;
        this.commandId = commandId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GuildCommandId that)) {
            return false;
        }
        return Objects.equals(guildId, that.guildId)
                && Objects.equals(commandId, that.commandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildId, commandId);
    }
}
