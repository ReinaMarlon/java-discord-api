package com.marlonreina.discord.api.infrastructure.adapter.out.persistence;

import com.marlonreina.discord.api.domain.model.AdsConfig;
import com.marlonreina.discord.api.domain.model.GuildCommandConfig;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.GuildFeatures;
import com.marlonreina.discord.api.domain.model.LogConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.AdsConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.CommandEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildCommandEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.LogConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.AdsConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.CommandJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildCommandJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.LogConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.WelcomeConfigJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GuildConfigRepositoryAdapter implements GuildConfigRepositoryPort {

    private static final String DEFAULT_PREFIX = "!";

    private final GuildConfigJpaRepository configRepository;
    private final WelcomeConfigJpaRepository welcomeRepository;
    private final LogConfigJpaRepository logRepository;
    private final AdsConfigJpaRepository adsRepository;
    private final CommandJpaRepository commandRepository;
    private final GuildCommandJpaRepository guildCommandRepository;

    public GuildConfigRepositoryAdapter(
            GuildConfigJpaRepository configRepository,
            WelcomeConfigJpaRepository welcomeRepository,
            LogConfigJpaRepository logRepository,
            AdsConfigJpaRepository adsRepository,
            CommandJpaRepository commandRepository,
            GuildCommandJpaRepository guildCommandRepository
    ) {
        this.configRepository = configRepository;
        this.welcomeRepository = welcomeRepository;
        this.logRepository = logRepository;
        this.adsRepository = adsRepository;
        this.commandRepository = commandRepository;
        this.guildCommandRepository = guildCommandRepository;
    }

    @Override
    public GuildConfigAggregate findOrCreateByGuildId(String guildId, String guildName) {
        GuildConfigEntity config = configRepository.findById(guildId)
                .orElseGet(() -> createDefaultConfig(guildId));

        WelcomeConfigEntity welcome = welcomeRepository.findById(guildId).orElse(null);
        LogConfigEntity logs = logRepository.findById(guildId).orElse(null);
        AdsConfigEntity ads = adsRepository.findById(guildId).orElse(null);

        return new GuildConfigAggregate(
                guildId,
                guildName,
                config.getPrefix(),
                config.isPremium(),
                mapFeatures(config, welcome),
                mapWelcome(welcome),
                mapLogs(logs),
                mapAds(ads),
                mapCommands(guildId)
        );
    }

    private GuildConfigEntity createDefaultConfig(String guildId) {
        GuildConfigEntity config = new GuildConfigEntity();
        config.setGuildId(guildId);
        config.setPrefix(DEFAULT_PREFIX);

        return configRepository.save(config);
    }

    private GuildFeatures mapFeatures(GuildConfigEntity config, WelcomeConfigEntity welcome) {
        return new GuildFeatures(
                welcome != null && welcome.isEnabled(),
                false,
                config.isPremium()
        );
    }

    private WelcomeConfig mapWelcome(WelcomeConfigEntity entity) {
        if (entity == null) {
            return null;
        }

        return new WelcomeConfig(
                entity.getChannelId(),
                entity.getMessage(),
                entity.getEmbedJson(),
                entity.isEnabled()
        );
    }

    private LogConfig mapLogs(LogConfigEntity entity) {
        if (entity == null) {
            return null;
        }

        return new LogConfig(
                entity.isEnabled(),
                entity.getChannelId()
        );
    }

    private AdsConfig mapAds(AdsConfigEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AdsConfig(
                entity.isEnabled(),
                entity.getChannelId()
        );
    }

    private List<GuildCommandConfig> mapCommands(String guildId) {
        Map<Long, GuildCommandEntity> guildCommands = guildCommandRepository.findByGuildId(guildId)
                .stream()
                .collect(Collectors.toMap(GuildCommandEntity::getCommandId, Function.identity()));

        return commandRepository.findAll()
                .stream()
                .map(command -> mapCommand(command, guildCommands.get(command.getCommandId())))
                .toList();
    }

    private GuildCommandConfig mapCommand(CommandEntity command, GuildCommandEntity guildCommand) {
        return new GuildCommandConfig(
                command.getCommandId(),
                command.getCommandName(),
                command.getCommandDescription(),
                command.getCommandPermissions(),
                command.isCommandPremium(),
                guildCommand != null && guildCommand.isEnabled()
        );
    }
}
