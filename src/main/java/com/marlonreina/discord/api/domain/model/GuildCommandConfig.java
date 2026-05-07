package com.marlonreina.discord.api.domain.model;

public class GuildCommandConfig {

    private final Long commandId;
    private final String commandName;
    private final String commandDescription;
    private final String commandPermissions;
    private final boolean commandPremium;
    private final boolean enabled;

    public GuildCommandConfig(
            Long commandId,
            String commandName,
            String commandDescription,
            String commandPermissions,
            boolean commandPremium,
            boolean enabled
    ) {
        this.commandId = commandId;
        this.commandName = commandName;
        this.commandDescription = commandDescription;
        this.commandPermissions = commandPermissions;
        this.commandPremium = commandPremium;
        this.enabled = enabled;
    }

    public Long getCommandId() {
        return commandId;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public String getCommandPermissions() {
        return commandPermissions;
    }

    public boolean isCommandPremium() {
        return commandPremium;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
