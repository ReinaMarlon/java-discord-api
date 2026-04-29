package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.DiscordChannel;
import com.marlonreina.discord.api.domain.model.DiscordGuild;
import com.marlonreina.discord.api.domain.model.DiscordMember;
import com.marlonreina.discord.api.domain.model.DiscordRole;
import com.marlonreina.discord.api.domain.model.DiscordUser;

import java.util.List;

public interface DiscordPort {

    DiscordUser getUser(String discordId);

    List<DiscordGuild> getUserGuilds(String discordId);

    List<DiscordChannel> getGuildChannels(String guildId);

    List<DiscordRole> getGuildRoles(String guildId);

    List<DiscordMember> getGuildMembers(String guildId);
}
