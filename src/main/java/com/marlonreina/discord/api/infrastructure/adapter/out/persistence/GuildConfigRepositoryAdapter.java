package com.marlonreina.discord.api.infrastructure.adapter.out.persistence;

import com.marlonreina.discord.api.domain.model.AdsConfig;
import com.marlonreina.discord.api.domain.model.GuildConfigAggregate;
import com.marlonreina.discord.api.domain.model.GuildFeatures;
import com.marlonreina.discord.api.domain.model.LogConfig;
import com.marlonreina.discord.api.domain.model.WelcomeConfig;
import com.marlonreina.discord.api.domain.port.out.GuildConfigRepositoryPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.AdsConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildFeaturesEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.LogConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeConfigEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.AdsConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.GuildFeaturesJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.LogConfigJpaRepository;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.WelcomeConfigJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GuildConfigRepositoryAdapter implements GuildConfigRepositoryPort {

    private static final String DEFAULT_PREFIX = "!";

    private final GuildConfigJpaRepository configRepository;
    private final GuildFeaturesJpaRepository featuresRepository;
    private final WelcomeConfigJpaRepository welcomeRepository;
    private final LogConfigJpaRepository logRepository;
    private final AdsConfigJpaRepository adsRepository;

    public GuildConfigRepositoryAdapter(
            GuildConfigJpaRepository configRepository,
            GuildFeaturesJpaRepository featuresRepository,
            WelcomeConfigJpaRepository welcomeRepository,
            LogConfigJpaRepository logRepository,
            AdsConfigJpaRepository adsRepository
    ) {
        this.configRepository = configRepository;
        this.featuresRepository = featuresRepository;
        this.welcomeRepository = welcomeRepository;
        this.logRepository = logRepository;
        this.adsRepository = adsRepository;
    }

    @Override
    public GuildConfigAggregate findOrCreateByGuildId(String guildId) {
        GuildConfigEntity config = configRepository.findById(guildId)
                .orElseGet(() -> createDefaultConfig(guildId));

        GuildFeaturesEntity features = featuresRepository.findById(guildId).orElse(null);
        WelcomeConfigEntity welcome = welcomeRepository.findById(guildId).orElse(null);
        LogConfigEntity logs = logRepository.findById(guildId).orElse(null);
        AdsConfigEntity ads = adsRepository.findById(Long.parseLong(guildId)).orElse(null);

        return new GuildConfigAggregate(
                guildId,
                config.getPrefix(),
                mapFeatures(features),
                mapWelcome(welcome),
                mapLogs(logs),
                mapAds(ads)
        );
    }

    private GuildConfigEntity createDefaultConfig(String guildId) {
        GuildConfigEntity config = new GuildConfigEntity();
        config.setGuildId(guildId);
        config.setPrefix(DEFAULT_PREFIX);

        return configRepository.save(config);
    }

    private GuildFeatures mapFeatures(GuildFeaturesEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GuildFeatures(
                entity.isWelcomeEnabled(),
                entity.isEconomyEnabled(),
                entity.isPremium()
        );
    }

    private WelcomeConfig mapWelcome(WelcomeConfigEntity entity) {
        if (entity == null) {
            return null;
        }

        return new WelcomeConfig(
                entity.getChannelId(),
                entity.getMessage()
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
}
