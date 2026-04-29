package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildConfigJpaRepository extends JpaRepository<GuildConfigEntity, String> {
}