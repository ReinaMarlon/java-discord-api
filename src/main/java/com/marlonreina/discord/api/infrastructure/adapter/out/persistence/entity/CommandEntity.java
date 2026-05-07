package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "commands")
public class CommandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "command_id")
    private Long commandId;

    @Column(name = "command_name")
    private String commandName;

    @Column(name = "command_description", columnDefinition = "TEXT")
    private String commandDescription;

    @Column(name = "command_permissions", nullable = false)
    private String commandPermissions;

    @Column(name = "command_premium", nullable = false)
    private boolean commandPremium;

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public void setCommandDescription(String commandDescription) {
        this.commandDescription = commandDescription;
    }

    public String getCommandPermissions() {
        return commandPermissions;
    }

    public void setCommandPermissions(String commandPermissions) {
        this.commandPermissions = commandPermissions;
    }

    public boolean isCommandPremium() {
        return commandPremium;
    }

    public void setCommandPremium(boolean commandPremium) {
        this.commandPremium = commandPremium;
    }
}
