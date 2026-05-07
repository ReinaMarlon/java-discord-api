package com.marlonreina.discord.api.infrastructure.adapter.out.persistence;

import com.marlonreina.discord.api.domain.model.AdsConfig;
import com.marlonreina.discord.api.domain.model.GuildCommandConfig;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.GuildFeatures;
import com.marlonreina.discord.api.domain.model.LogConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfigUpdate;
import com.marlonreina.discord.api.domain.model.WelcomeImage;
import com.marlonreina.discord.api.domain.model.WelcomeImageUpdate;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.AdsConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.CommandEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildCommandEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.LogConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeImageEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.AdsConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.CommandJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildCommandJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.LogConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.WelcomeConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.WelcomeImageJpaRepository;
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
    private final WelcomeImageJpaRepository welcomeImageRepository;

    public GuildConfigRepositoryAdapter(
            GuildConfigJpaRepository configRepository,
            WelcomeConfigJpaRepository welcomeRepository,
            LogConfigJpaRepository logRepository,
            AdsConfigJpaRepository adsRepository,
            CommandJpaRepository commandRepository,
            GuildCommandJpaRepository guildCommandRepository,
            WelcomeImageJpaRepository welcomeImageRepository
    ) {
        this.configRepository = configRepository;
        this.welcomeRepository = welcomeRepository;
        this.logRepository = logRepository;
        this.adsRepository = adsRepository;
        this.commandRepository = commandRepository;
        this.guildCommandRepository = guildCommandRepository;
        this.welcomeImageRepository = welcomeImageRepository;
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

    @Override
    public WelcomeConfig saveWelcomeConfig(WelcomeConfigUpdate update) {
        configRepository.findById(update.getGuildId())
                .orElseGet(() -> createDefaultConfig(update.getGuildId()));

        WelcomeConfigEntity welcome = welcomeRepository.findById(update.getGuildId())
                .orElseGet(() -> createDefaultWelcomeConfig(update.getGuildId()));

        welcome.setEnabled(update.isEnabled());
        welcome.setChannelId(update.getChannelId());
        welcome.setMessage(update.getMessage());
        welcome.setEmbedJson(update.getEmbedJson());

        return mapWelcome(welcomeRepository.save(welcome));
    }

    @Override
    public WelcomeImage saveWelcomeImage(WelcomeImageUpdate update) {
        configRepository.findById(update.getGuildId())
                .orElseGet(() -> createDefaultConfig(update.getGuildId()));

        WelcomeImageEntity image = welcomeImageRepository.findById(update.getGuildId())
                .orElseGet(() -> createDefaultWelcomeImage(update.getGuildId()));

        image.setImageUrl(update.getImageUrl());
        image.setImageName(update.getImageName());
        image.setImageData(update.getImageData());
        image.setImageHash(update.getImageHash());
        image.setMimeType(update.getMimeType());
        image.setWidth(update.getWidth());
        image.setHeight(update.getHeight());

        return mapWelcomeImage(welcomeImageRepository.save(image));
    }

    private GuildConfigEntity createDefaultConfig(String guildId) {
        GuildConfigEntity config = new GuildConfigEntity();
        config.setGuildId(guildId);
        config.setPrefix(DEFAULT_PREFIX);

        return configRepository.save(config);
    }

    private WelcomeConfigEntity createDefaultWelcomeConfig(String guildId) {
        WelcomeConfigEntity welcome = new WelcomeConfigEntity();
        welcome.setGuildId(guildId);

        return welcome;
    }

    private WelcomeImageEntity createDefaultWelcomeImage(String guildId) {
        WelcomeImageEntity image = new WelcomeImageEntity();
        image.setGuildId(guildId);

        return image;
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

    private WelcomeImage mapWelcomeImage(WelcomeImageEntity entity) {
        return new WelcomeImage(
                entity.getGuildId(),
                entity.getImageUrl(),
                entity.getImageHash(),
                entity.getMimeType(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getUpdatedAt()
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
