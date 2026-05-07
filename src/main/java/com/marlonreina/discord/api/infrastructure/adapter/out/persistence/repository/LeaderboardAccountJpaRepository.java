package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.LeaderboardAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderboardAccountJpaRepository extends JpaRepository<LeaderboardAccountEntity, Long> {

    List<LeaderboardAccountEntity> findByGuildId(String guildId);

    Optional<LeaderboardAccountEntity> findByGuildIdAndDiscordId(String guildId, String discordId);
}
