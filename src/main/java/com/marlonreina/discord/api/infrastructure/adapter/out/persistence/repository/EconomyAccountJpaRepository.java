package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.EconomyAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EconomyAccountJpaRepository extends JpaRepository<EconomyAccountEntity, Long> {

    List<EconomyAccountEntity> findByGuildId(String guildId);

    Optional<EconomyAccountEntity> findByGuildIdAndDiscordId(String guildId, String discordId);
}
