package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildCommandEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildCommandId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuildCommandJpaRepository extends JpaRepository<GuildCommandEntity, GuildCommandId> {

    List<GuildCommandEntity> findByGuildId(String guildId);
}
