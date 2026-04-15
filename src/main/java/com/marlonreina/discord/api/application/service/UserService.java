package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.port.in.UserUseCase;
import com.marlonreina.discord.api.domain.port.out.DiscordPort;
import com.marlonreina.discord.api.shared.dto.GuildResponse;
import com.marlonreina.discord.api.shared.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserUseCase {

    private final DiscordPort discordPort;

    public UserService(DiscordPort discordPort) {
        this.discordPort = discordPort;
    }

    @Override
    public UserResponse getUserData(String discordId) {

        var user = discordPort.getUser(discordId);
        var guilds = discordPort.getUserGuilds(discordId);

        List<GuildResponse> filteredGuilds = guilds.stream()
                .filter(g -> isOwner(g) || isAdmin(g))
                .sorted(this::sortGuilds)
                .map(this::mapToResponse)
                .toList();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAvatar(),
                filteredGuilds
        );
    }

    private boolean isOwner(DiscordGuild guild) {
        return guild.isOwner();
    }

    private boolean isAdmin(DiscordGuild guild) {
        long permissions = guild.getPermissions();
        return (permissions & 0x8) == 0x8;
    }

    private int sortGuilds(DiscordGuild a, DiscordGuild b) {

        if (a.isOwner() && !b.isOwner()) {
            return -1;
        }
        if (!a.isOwner() && b.isOwner()) {
            return 1;
        }
        if (isAdmin(a) && !isAdmin(b)) {
            return -1;
        }
        if (!isAdmin(a) && isAdmin(b)) {
            return 1;
        }

        return 0;
    }

    private GuildResponse mapToResponse(DiscordGuild g) {
        return new GuildResponse(
                g.getId(),
                g.getName(),
                g.getIcon(),
                g.getBanner()
        );
    }
}